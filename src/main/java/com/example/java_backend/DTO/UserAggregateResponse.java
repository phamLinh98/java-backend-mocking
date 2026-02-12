package com.example.java_backend.DTO;

import java.util.List;

public class UserAggregateResponse {

        private List<UserDTO> users;
        private List<ExternalDataDTO> externalData;

        public UserAggregateResponse(
                        List<UserDTO> users,
                        List<ExternalDataDTO> externalData) {
                this.users = users;
                this.externalData = externalData;
        }

        public List<UserDTO> getUsers() {
                return users;
        }

        public List<ExternalDataDTO> getExternalData() {
                return externalData;
        }
}
