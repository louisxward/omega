package org.com.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.com.entity.User;
import org.com.entity.UserCredential;
import org.com.model.AuthRequest;
import org.com.model.AuthResponse;
import org.jboss.logging.Logger;

import java.security.SecureRandom;
import java.util.ArrayList;
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
    
    @Transactional
    public AuthResponse register(AuthRequest authRequest) {
        var username = authRequest.username();
        var password = authRequest.password();
        var messages = new ArrayList<String>();
        if (null == username || null == password) {
            messages.add("Please enter details");
            return new AuthResponse(false, messages);
        }
        if (!uniqueUsername(username)) messages.add("Username not unique");
        if (!validPassword(password)) messages.add("Password not valid");
        if (!messages.isEmpty()) return new AuthResponse(false, messages);
        var user = createUser(username);
        createCredential(user, password);
        return String.format("User created '%s'", user.id);
    }
    
    private boolean uniqueUsername(String username) {
        return User.find("username", username).count() == 0;
    }
    
    private boolean validPassword(String password) {
        return null != password && password.length() > 4;
    }
    
    private User createUser(String username) {
        var user = new User();
        user.username = username;
        user.persist();
        return user;
    }
    
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
