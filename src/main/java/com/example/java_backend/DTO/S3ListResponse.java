package com.example.java_backend.DTO;

import java.util.List;

public class S3ListResponse {
        private String bucketName;
        private List<S3ObjectDTO> objects;
        private Integer totalObjects;

        public S3ListResponse() {
        }

        public S3ListResponse(String bucketName, List<S3ObjectDTO> objects, Integer totalObjects) {
                this.bucketName = bucketName;
                this.objects = objects;
                this.totalObjects = totalObjects;
        }

        // Getters and Setters
        public String getBucketName() {
                return bucketName;
        }

        public void setBucketName(String bucketName) {
                this.bucketName = bucketName;
        }

        public List<S3ObjectDTO> getObjects() {
                return objects;
        }

        public void setObjects(List<S3ObjectDTO> objects) {
                this.objects = objects;
        }

        public Integer getTotalObjects() {
                return totalObjects;
        }

        public void setTotalObjects(Integer totalObjects) {
                this.totalObjects = totalObjects;
        }
}
