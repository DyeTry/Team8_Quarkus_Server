package org.team8.authentication;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;


    @POST
    @Path("/login")
    public LoginResponse login(LoginRequest req) {
        // 1) Find user from DB
        AppUser user = AppUser.find("username", req.getUsername()).firstResult();
        if (user == null) {
            throw new WebApplicationException("Invalid username or password", 401);
        }

        // 2) Authenticate and build JWT (includes groups)
        String token = authService.authenticate(req.getUsername(), req.getPassword());
        if (token == null) {
            throw new WebApplicationException("Invalid username or password", 401);
        }

        // 3) Return *DB role*, NOT hard-coded USER
        String role = user.getRole();  // should be ADMIN / MANAGER / TECH / USER
        System.out.println("[AuthResource] login user=" + user.getUsername() + " role=" + role);
        return new LoginResponse(token, role);
    }
}
