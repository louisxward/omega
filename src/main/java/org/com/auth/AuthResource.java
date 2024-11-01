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
import org.com.model.AuthRequest;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

@Path("/auth")
public class AuthResource {
    
    private static final String ISSUER = "https://example.com/issuer"; // Store this securely!
    private static final String PEPPER = "my_secret_pepper"; // Store this securely!
    
    @POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateToken(AuthRequest authRequest) {
        var username = authRequest.username();
        var password = authRequest.password();
        Optional<User> user = User.find("username", username).firstResultOptional();
        if (user.isPresent()) {
            Optional<UserCredential> credential = UserCredential.find("user", user.get()).firstResultOptional();
            if (credential.isPresent()) {
                var saltedPassword = credential.get().salt + password + PEPPER;
                if (BcryptUtil.matches(saltedPassword, credential.get().passwordHash)) {
                    var token =
                        Jwt.issuer(ISSUER)
                            .upn(username)
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
        var user = new User();
        user.username = authRequest.username();
        user.persist();
        var random = new SecureRandom();
        var saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        var salt = Base64.getEncoder().encodeToString(saltBytes);
        var seasonedPassword = salt + authRequest.password() + PEPPER;
        var passwordHash = BcryptUtil.bcryptHash(seasonedPassword);
        var credential = new UserCredential();
        credential.user = user;
        credential.passwordHash = passwordHash;
        credential.salt = salt;
        credential.persist();
        return Response.status(Response.Status.CREATED).build();
    }
}