package com.example.java_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.java_backend.DTO.ExternalDataDTO;
import com.example.java_backend.DTO.UserAggregateResponse;
import com.example.java_backend.DTO.UserDTO;

@Service
public class UserAggregationService {

        private final UserService userService;
        private final ExternalApiService externalApiService;

        public UserAggregationService(
                        UserService userService,
                        ExternalApiService externalApiService) {
                this.userService = userService;
                this.externalApiService = externalApiService;
        }

        public UserAggregateResponse getUsersWithExternalData() {
                List<UserDTO> users = userService.getAllUsers();
                List<ExternalDataDTO> externalData = externalApiService.fetchExternalData();

                return new UserAggregateResponse(users, externalData);
        }
}
