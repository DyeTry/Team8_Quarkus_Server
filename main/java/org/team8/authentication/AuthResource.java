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
    public Response login(LoginRequest request) {
        String token = authService.authenticate(request.username, request.password);
        if (token == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Invalid username or password\"}")
                    .build();
        }

        LoginResponse resp = new LoginResponse();
        resp.token = token;
        resp.role = "USER";
        return Response.ok(resp).build();
    }
}
