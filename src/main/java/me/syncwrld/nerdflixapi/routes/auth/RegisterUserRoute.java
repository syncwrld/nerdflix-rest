package me.syncwrld.nerdflixapi.routes.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import me.syncwrld.nerdflixapi.extend.SimpleReplier;
import me.syncwrld.nerdflixapi.holder.MemoryHolder;
import me.syncwrld.nerdflixapi.management.AuthorizationManager;
import me.syncwrld.nerdflixapi.model.authentication.User;
import me.syncwrld.streets1.auth.controller.AuthTokenController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.UUID;

@RestController
public class RegisterUserRoute extends SimpleReplier {

    private final AuthTokenController authTokenController = new AuthTokenController();
    private final AuthorizationManager authorizationManager = new AuthorizationManager();

    @SneakyThrows
    @PostMapping("/auth/register")
    public String handleRegisterUser(String token, String username, String email, String password, HttpServletResponse response) {
        if (token == null) {
            response.setStatus(400);
            return asJson(Map.of("error", "Missing API Key"));
        }

        if (username == null || email == null || password == null) {
            response.setStatus(400);
            return asJson(Map.of("error", "Missing username, email, or password"));
        }

        if (!authorizationManager.checkAuthorization(token)) {
            response.sendError(403, "Invalid API Key");
            return asJson(Map.of("error", "Invalid API Key"));
        }

        String salt = authTokenController.getRandomSalt();
        String encryptedPassword;

        try {
            encryptedPassword = authTokenController.encryptPassword(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        User byEmail = MemoryHolder.USER.findByEmail(email);

        if (byEmail != null) {
            response.setStatus(400);
            return asJson(Map.of("error", "Email already in use"));
        }

        User user = new User(UUID.randomUUID(), username, email, encryptedPassword, salt);
        UUID uuid = user.getUuid();

        MemoryHolder.USER.insertUser(uuid, user);
        response.setContentType("application/json");
        response.setStatus(200);

        return asJson(Map.of("uuid", uuid.toString()));
    }

}
