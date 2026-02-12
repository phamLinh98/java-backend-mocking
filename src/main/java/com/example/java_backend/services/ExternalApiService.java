package com.example.java_backend.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.java_backend.DTO.ExternalDataDTO;

@Service
public class ExternalApiService {

        private final RestTemplate restTemplate;
        
        @Value("${external.api.todo.url}")
        private String todoApiUrl;

        public ExternalApiService(RestTemplate restTemplate) {
                this.restTemplate = restTemplate;
        }

        public List<ExternalDataDTO> fetchExternalData() {
                String url = todoApiUrl;

                ResponseEntity<ExternalDataDTO[]> response = restTemplate.getForEntity(url, ExternalDataDTO[].class);

                return Arrays.asList(response.getBody());
        }
}
