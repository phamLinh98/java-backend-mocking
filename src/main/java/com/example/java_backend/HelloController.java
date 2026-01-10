package com.example.java_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    @GetMapping("/")
    public Map<String, Object> hello(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello");
        Object printOutValue = request.getAttribute("printOutValue");
        response.put("printOut", printOutValue != null ? printOutValue : "");
        return response;
    }
}
