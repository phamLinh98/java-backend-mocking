package com.example.java_backend.DTO;

import java.time.Instant;

public class S3ObjectDTO {
        private String key;
        private Long size;
        private Instant lastModified;
        private String eTag;
        private String storageClass;

        public S3ObjectDTO() {
        }

        public S3ObjectDTO(String key, Long size, Instant lastModified, String eTag, String storageClass) {
                this.key = key;
                this.size = size;
                this.lastModified = lastModified;
                this.eTag = eTag;
                this.storageClass = storageClass;
        }

        // Getters and Setters
        public String getKey() {
                return key;
        }

        public void setKey(String key) {
                this.key = key;
        }

        public Long getSize() {
                return size;
        }

        public void setSize(Long size) {
                this.size = size;
        }

        public Instant getLastModified() {
                return lastModified;
        }

        public void setLastModified(Instant lastModified) {
                this.lastModified = lastModified;
        }

        public String geteTag() {
                return eTag;
        }

        public void seteTag(String eTag) {
                this.eTag = eTag;
        }

        public String getStorageClass() {
                return storageClass;
        }

        public void setStorageClass(String storageClass) {
                this.storageClass = storageClass;
        }
}
