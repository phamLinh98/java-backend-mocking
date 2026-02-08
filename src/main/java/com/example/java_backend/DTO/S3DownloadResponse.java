package com.example.java_backend.DTO;

public class S3DownloadResponse {
        private String fileName;
        private String contentType;
        private Long contentLength;
        private byte[] content;
        private String message;

        public S3DownloadResponse() {
        }

        public S3DownloadResponse(String fileName, String contentType, Long contentLength, byte[] content) {
                this.fileName = fileName;
                this.contentType = contentType;
                this.contentLength = contentLength;
                this.content = content;
        }

        // Getters and Setters
        public String getFileName() {
                return fileName;
        }

        public void setFileName(String fileName) {
                this.fileName = fileName;
        }

        public String getContentType() {
                return contentType;
        }

        public void setContentType(String contentType) {
                this.contentType = contentType;
        }

        public Long getContentLength() {
                return contentLength;
        }

        public void setContentLength(Long contentLength) {
                this.contentLength = contentLength;
        }

        public byte[] getContent() {
                return content;
        }

        public void setContent(byte[] content) {
                this.content = content;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }
}
