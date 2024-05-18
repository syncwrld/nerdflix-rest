package me.syncwrld.nerdflixapi.routes.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import me.syncwrld.nerdflixapi.extend.SimpleReplier;
import me.syncwrld.nerdflixapi.holder.MemoryHolder;
import me.syncwrld.nerdflixapi.management.AuthorizationManager;
import me.syncwrld.nerdflixapi.model.authentication.User;
import me.syncwrld.streets1.auth.controller.AuthTokenController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@RestController
public class LoginUserRoute extends SimpleReplier {

    private final AuthTokenController authTokenController = new AuthTokenController();
    private final AuthorizationManager authorizationManager = new AuthorizationManager();

    @SneakyThrows
    @GetMapping("/auth/login")
    public String handleRegisterUser(String token, String email, String password, HttpServletResponse response) {
        if (token == null) {
            response.setStatus(400);
            return asJson(Map.of("error", "Missing API Key"));
        }

        if (!authorizationManager.checkAuthorization(token)) {
            response.sendError(403, "Invalid API Key");
            return asJson(Map.of("error", "Invalid API Key"));
        }

        if (email == null || password == null) {
            response.setStatus(400);
            return asJson(Map.of("error", "Missing email or password"));
        }

        User user = MemoryHolder.USER.findByEmail(email);

        if (user == null) {
            response.setStatus(400);
            return asJson(Map.of("error", "No users with this email address not found"));
        }

        String saltToken = user.getSaltToken();

        if (saltToken == null) {
            response.setStatus(400);
            return asJson(Map.of("error", "No salt token found for this user"));
        }

        boolean validPassword;
        try {
            validPassword = authTokenController.verifyPassword(password, saltToken, user.getPassword());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            response.setStatus(500);
            return asJson(Map.of("error", "Internal server error while verifying password"));
        }

        response.setContentType("application/json");
        response.setStatus(200);

        return asJson(Map.of("status", validPassword));
    }

}
