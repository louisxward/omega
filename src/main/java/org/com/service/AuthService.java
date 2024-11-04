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
            logger.info(String.format("verifyAndGenerateToken - user found '%s'", user.id));
            Optional<UserCredential> optCredential = UserCredential.find("user", user).firstResultOptional();
            if (optCredential.isPresent()) {
                var credential = optCredential.get();
                var seasonedPassword = credential.salt + authRequest.password() + PEPPER;
                if (BcryptUtil.matches(seasonedPassword, credential.passwordHash)) {
                    logger.info(String.format("verifyAndGenerateToken - user authorised '%s'", user.id));
                    var token = Jwt.issuer(ISSUER)
                        .upn(authRequest.username())
                        .groups(Set.of("standard"))
                        .sign();
                    return Response.ok(token).build();
                }
            }
        }
        logger.warn("verifyAndGenerateToken - BAD_REQUEST");
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @Transactional
    public Response validateAndRegister(AuthRequest authRequest) {
        if (!verifyUniqueUsername(authRequest.username())) {
            throw new ValidationException("register.authRequest.username", "Username not unique");
        }
        var user = createUser(authRequest.username());
        createCredential(user, authRequest.password());
        logger.info(String.format("validateAndRegister - user created '%s'", user.id));
        return Response.ok().build();
        
    }
    
    private boolean verifyUniqueUsername(String username) {
        return User.find("username", username).count() == 0;
    }
    
    private User createUser(String username) {
        var user = new User();
        user.username = username;
        user.persist();
        return user;
    }
    
    private String passSalt() {
        var random = new SecureRandom();
        var saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
    
    private void createCredential(User user, String password) {
        var salt = passSalt();
        var seasonedPassword = salt + password + PEPPER;
        var passwordHash = BcryptUtil.bcryptHash(seasonedPassword);
        var credential = new UserCredential();
        credential.user = user;
        credential.passwordHash = passwordHash;
        credential.salt = salt;
        credential.persist();
    }
}
