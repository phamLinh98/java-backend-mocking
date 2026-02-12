package com.example.java_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_backend.DTO.UserAggregateResponse;
import com.example.java_backend.services.UserAggregationService;

@RestController
@RequestMapping("/api/aggregate")
public class UserAggregateController {

        private final UserAggregationService userAggregationService;

        public UserAggregateController(UserAggregationService userAggregationService) {
                this.userAggregationService = userAggregationService;
        }

        /**
         * GET /api/users/aggregate
         * Return users from DB + data from external API
         */
        @GetMapping("/aggregate")
        public ResponseEntity<UserAggregateResponse> getUsersWithExternalData() {
                UserAggregateResponse response = userAggregationService.getUsersWithExternalData();

                return ResponseEntity.ok(response);
        }
}
