package com.example.java_backend.interceptors;

import com.example.java_backend.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Skip authentication for public endpoints
        // String path = request.getRequestURI();
        
        // // Allow these endpoints without authentication
        // if (path.equals("/api/auth/login") || 
        //     path.equals("/api/auth/refresh-token") || 
        //     path.equals("/api/hello") ||
        //     path.startsWith("/error")) {
        //     return true;
        // }

        // Get access token from cookie
        String accessToken = getTokenFromCookie(request, "accessToken");

        if (accessToken == null || accessToken.isEmpty()) {
            sendErrorResponse(response, 401, "Unauthorized", "Access token is missing");
            return false;
        }

        try {
            // Validate token
            jwtUtil.validateToken(accessToken);
            
            // Verify it's an access token
            if (!jwtUtil.isTokenType(accessToken, "access")) {
                sendErrorResponse(response, 401, "Unauthorized", "Invalid token type");
                return false;
            }

            // Extract user info and set to request attribute for later use
            Long userId = jwtUtil.extractUserId(accessToken);
            String username = jwtUtil.extractUsername(accessToken);
            
            request.setAttribute("userId", userId);
            request.setAttribute("username", username);

            return true;

        } catch (Exception e) {
            sendErrorResponse(response, 401, "Unauthorized", "Invalid token: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get token from cookie
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

    /**
     * Send error response as JSON
     */
    private void sendErrorResponse(HttpServletResponse response, int status, String error, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        errorResponse.put("timestamp", System.currentTimeMillis());

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
