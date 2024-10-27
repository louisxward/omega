package org.com.auth;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.com.entity.User;
import org.com.entity.UserAuth;

import java.util.Set;

@Path("/auth")
public class AuthResource {

    @POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateToken(AuthRequest authRequest) {
        if (verifyCredentials(authRequest.username(), authRequest.password())) {
            String token =
                    Jwt.issuer("CHANGE_MEEEEEEEEEEEEEEE")
                            .upn(authRequest.username())
                            // ToDo - Set Group
                            .groups(Set.of("standard", "admin"))
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
        if (null == user) return false;
        // ToDo - Not sure on this
        UserAuth userAuth = UserAuth.find("user", user).firstResult();
        return null != userAuth && userAuth.getPassword().equals(password);
    }
}