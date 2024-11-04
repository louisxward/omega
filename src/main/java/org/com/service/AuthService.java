package org.com.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.com.entity.User;
import org.com.entity.UserCredential;
import org.com.exception.ValidationException;
import org.com.model.AuthRequest;
import org.jboss.logging.Logger;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

@RequestScoped
public class AuthService {
    
    private static final String ISSUER = "https://example.com/issuer";
    private static final String PEPPER = "my_secret_pepper";
    
    @Inject
    Logger logger;
    
    public Response verifyAndGenerateToken(AuthRequest authRequest) {
        Optional<User> optUser = User.find("username", authRequest.username()).firstResultOptional();
        if (optUser.isPresent()) {
            var user = optUser.get();
            Optional<UserCredential> optCredential = UserCredential.find("user", user).firstResultOptional();
            if (optCredential.isPresent()) {
                var credential = optCredential.get();
                var seasonedPassword = credential.salt + authRequest.password() + PEPPER;
                if (BcryptUtil.matches(seasonedPassword, credential.passwordHash)) {
                    logger.info(String.format("verifyAndGenerateToken - user authorised '%s'", user.id));
                    var token = Jwt.issuer(ISSUER)
                        .upn(authRequest.username())
                        // ToDo - Set admin if admin
                        .groups(Set.of("standard"))
                        .sign();
                    return Response.ok(token).build();
                }
            }
            else {
                logger.error(String.format("verifyAndGenerateToken - user with no credential '%s'", user.id));
            }
        }
        throw new ValidationException("authRequest", "incorrect username or password");
    }
    
    @Transactional
    public Response validateAndRegister(AuthRequest authRequest) {
        if (User.find("username", authRequest.username()).count() > 0) {
            throw new ValidationException("username", "Username not unique");
        }
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
        logger.info(String.format("validateAndRegister - user created '%s'", user.id));
        return Response.ok().build();
    }
    
}
