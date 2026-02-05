package com.example.java_backend.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_backend.DTO.ErrorResponse;
import com.example.java_backend.DTO.LoginRequest;
import com.example.java_backend.DTO.LoginResponse;
import com.example.java_backend.entities.User;
import com.example.java_backend.repositories.UserRepository;
import com.example.java_backend.utils.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Login API - Generate and set Access Token & Refresh Token in cookies
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            // Find user by username
            Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Unauthorized", "Invalid username or password", 401));
            }

            User user = userOptional.get();

            // Simple password verification (in production, use BCrypt)
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Unauthorized", "Invalid username or password", 401));
            }

            // Generate tokens
            String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());

            // Set tokens in cookies
            setTokenCookie(response, "accessToken", accessToken, 3600); // 1 hour
            setTokenCookie(response, "refreshToken", refreshToken, 86400); // 24 hours

            // Return success response
            LoginResponse loginResponse = new LoginResponse(
                    "Login successful",
                    user.getId(),
                    user.getUsername());

            return ResponseEntity.ok(loginResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error", "Login failed: " + e.getMessage(), 500));
        }
    }

    /**
     * Refresh Token API - Clear old tokens and set new AT + RT in cookies
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Get refresh token from cookie
            String refreshToken = getTokenFromCookie(request, "refreshToken");

            if (refreshToken == null || refreshToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Unauthorized", "Refresh token is missing", 401));
            }

            // Validate refresh token
            jwtUtil.validateToken(refreshToken);

            // Verify it's a refresh token
            if (!jwtUtil.isTokenType(refreshToken, "refresh")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Unauthorized", "Invalid token type", 401));
            }

            // Extract user info
            Long userId = jwtUtil.extractUserId(refreshToken);
            String username = jwtUtil.extractUsername(refreshToken);

            // Clear old tokens
            clearTokenCookie(response, "accessToken");
            clearTokenCookie(response, "refreshToken");

            // Generate new tokens
            String newAccessToken = jwtUtil.generateAccessToken(userId, username);
            String newRefreshToken = jwtUtil.generateRefreshToken(userId, username);

            // Set new tokens in cookies
            setTokenCookie(response, "accessToken", newAccessToken, 3600);
            setTokenCookie(response, "refreshToken", newRefreshToken, 86400);

            Map<String, Object> result = new HashMap<>();
            result.put("message", "Tokens refreshed successfully");
            result.put("userId", userId);
            result.put("username", username);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Unauthorized", "Invalid refresh token: " + e.getMessage(), 401));
        }
    }

    /**
     * Logout API - Clear tokens from cookies
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        try {
            // Clear both tokens
            clearTokenCookie(response, "accessToken");
            clearTokenCookie(response, "refreshToken");

            Map<String, Object> result = new HashMap<>();
            result.put("message", "Logout successful");

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error", "Logout failed: " + e.getMessage(), 500));
        }
    }

    /**
     * Helper method to set token in cookie
     */
    private void setTokenCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * Helper method to clear token cookie
     */
    private void clearTokenCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expire immediately
        response.addCookie(cookie);
    }

    /**
     * Helper method to get token from cookie
     */
    private String getTokenFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
