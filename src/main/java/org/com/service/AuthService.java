package org.com.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.com.entity.User;
import org.com.entity.UserCredential;
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
    
    public Optional<String> generateToken(AuthRequest authRequest) {
        logger.info("generateToken");
        var username = authRequest.username();
        var password = authRequest.password();
        Optional<User> optUser = User.find("username", username).firstResultOptional();
        if (optUser.isPresent()) {
            var user = optUser.get();
            logger.info(String.format("generateToken - user found '%s'", user.id));
            Optional<UserCredential> credential = UserCredential.find("user", user).firstResultOptional();
            if (credential.isPresent()) {
                var saltedPassword = credential.get().salt + password + PEPPER;
                if (BcryptUtil.matches(saltedPassword, credential.get().passwordHash)) {
                    logger.info(String.format("generateToken - user authorised '%s'", user.id));
                    var token = Jwt.issuer(ISSUER)
                        .upn(username)
                        .groups(Set.of("standard"))
                        .sign();
                    return Optional.of(token);
                }
            }
        }
        logger.warn("generateToken - invalid details");
        return Optional.empty();
    }
    
    public boolean register(AuthRequest authRequest) {
        User user;
        try {
            user = createUser(authRequest.username());
        } catch (Exception e) {
            logger.warn(String.format("register - %s", e.getMessage()));
            return false;
        }
        createCredential(user, authRequest.username());
        logger.info(String.format("register - user registered '%s'", user.id));
        return true;
    }
    
    @Transactional
    private User createUser(String username) {
        var user = new User();
        user.username = username;
        user.persist();
        return user;
    }
    
    @Transactional
    private void createCredential(User user, String password) {
        var random = new SecureRandom();
        var saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        var salt = Base64.getEncoder().encodeToString(saltBytes);
        var seasonedPassword = salt + password + PEPPER;
        var passwordHash = BcryptUtil.bcryptHash(seasonedPassword);
        var credential = new UserCredential();
        credential.user = user;
        credential.passwordHash = passwordHash;
        credential.salt = salt;
        credential.persist();
    }
}
