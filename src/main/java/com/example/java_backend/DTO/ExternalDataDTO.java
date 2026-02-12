package com.example.java_backend.DTO;

public class ExternalDataDTO {

        private String id;
        private String title;
        private String completed;

        public ExternalDataDTO() {
        }

        public ExternalDataDTO(String id, String title, String completed) {
                this.id = id;
                this.title = title;
                this.completed = completed;
        }

        public String getId() {
                return id;
        }

        public String getTitle() {
                return title;
        }

        public String getCompleted() {
                return completed;
        }
}
