package org.team8.dev;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.mindrot.jbcrypt.BCrypt;

@Path("/api/dev")
@Produces(MediaType.TEXT_PLAIN)
public class DevHashResource {
    @GET
    @Path("/hash/{pw}")
    public String hash(@PathParam("pw") String pw) {
        return BCrypt.hashpw(pw, BCrypt.gensalt(12));
    }
}