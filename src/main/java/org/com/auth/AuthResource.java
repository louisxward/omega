package org.com.auth;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.com.entity.User;
import org.com.entity.UserCredential;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Set;

@Path("/auth")
public class AuthResource {

    private static final String ISSUER = "the_omega-application-3million"; // Store this securely!
    private static final String PEPPER = "my_secret_pepper"; // Store this securely!

    @POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateToken(AuthRequest authRequest) {
        String username = authRequest.username();
        String password = authRequest.password();
        User user = User.find("username", username).firstResult();
        if (user != null) {
            UserCredential credential = UserCredential.find("user", user).firstResult();
            if (credential != null) {
                String saltedPassword = credential.getSalt() + password + PEPPER;
                if (BcryptUtil.matches(saltedPassword, credential.getPasswordHash())) {
                    String token =
                            Jwt.issuer(ISSUER)
                                    .upn(username)
                                    // ToDo - set roles from db
                                    .groups(Set.of("standard", "admin"))
                                    .sign();
                    return Response.ok().entity(token).build();
                }
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/register")
    @Transactional
    public Response register(AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.username());
        user.persist();
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        // Hash the password with salt and pepper
        String saltedPassword = salt + authRequest.password() + PEPPER;
        String passwordHash = BcryptUtil.bcryptHash(saltedPassword);
        UserCredential credential = new UserCredential();
        credential.setUser(user);
        credential.setPasswordHash(passwordHash);
        credential.setSalt(salt);
        credential.persist();
        return Response.status(201).build();
    }
}