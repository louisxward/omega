package org.com.auth;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.com.entity.User;

import java.util.Set;

@Path("/auth")
public class AuthResource {

    @POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateToken(User credentials) {
        if (verifyCredentials(credentials.getUsername(), credentials.getPassword())) {
            String token =
                    Jwt.issuer("https://example.com/issuer")
                            .upn(credentials.getUsername())
                            .groups(Set.of("user"))
                            .sign();
            return Response.ok().entity(token).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean verifyCredentials(String username, String password) {
        // TODO: Implement your actual authentication logic here
        // This is a placeholder, you should hash and compare passwords securely
        User user = User.find("username", username).firstResult();
        return user != null && user.getPassword().equals(password);
    }
}