package com.example.java_backend.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.java_backend.DTO.S3DownloadResponse;
import com.example.java_backend.DTO.S3ListResponse;
import com.example.java_backend.DTO.S3ObjectDTO;
import com.example.java_backend.services.S3Service;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

        private final S3Service s3Service;

        // ✅ Constructor Injection (CHUẨN)
        public S3Controller(S3Service s3Service) {
                this.s3Service = s3Service;
        }

        /**
         * GET /api/s3/test - Test S3 connection
         */
        @GetMapping("/test")
        public ResponseEntity<Map<String, String>> testConnection() {
                Map<String, String> response = new HashMap<>();
                try {
                        s3Service.listObjects();
                        response.put("status", "ok");
                        return ResponseEntity.ok(response);
                } catch (Exception e) {
                        response.put("status", "error");
                        response.put("message", e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
        }

        /**
         * GET /api/s3/objects
         */
        @GetMapping("/objects")
        public ResponseEntity<S3ListResponse> listObjects() {
                return ResponseEntity.ok(s3Service.listObjects());
        }

        /**
         * GET /api/s3/objects/search?prefix=folder/
         */
        @GetMapping("/objects/search")
        public ResponseEntity<S3ListResponse> listObjectsByPrefix(
                        @RequestParam(required = false, defaultValue = "") String prefix) {

                return ResponseEntity.ok(s3Service.listObjectsByPrefix(prefix));
        }

        /**
         * GET /api/s3/download/{key}
         */
        @GetMapping("/download/{*key}")
        public ResponseEntity<byte[]> downloadFile(@PathVariable String key) {

                S3DownloadResponse response = s3Service.downloadFile(key);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(response.getContentType()));
                headers.setContentDispositionFormData("attachment", response.getFileName());
                headers.setContentLength(response.getContentLength());

                return new ResponseEntity<>(response.getContent(), headers, HttpStatus.OK);
        }

        /**
         * GET /api/s3/download?key=file.jpg - Alternative với query parameter
         */
        @GetMapping("/download")
        public ResponseEntity<byte[]> downloadFileByQuery(@RequestParam String key) {

                S3DownloadResponse response = s3Service.downloadFile(key);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(response.getContentType()));
                headers.setContentDispositionFormData("attachment", response.getFileName());
                headers.setContentLength(response.getContentLength());

                return new ResponseEntity<>(response.getContent(), headers, HttpStatus.OK);
        }

        /**
         * POST /api/s3/upload
         */
        @PostMapping("/upload")
        public ResponseEntity<Map<String, String>> uploadFile(
                        @RequestParam("file") MultipartFile file,
                        @RequestParam(value = "key", required = false) String key) {

                String fileKey = (key != null && !key.isEmpty())
                                ? key
                                : file.getOriginalFilename();

                s3Service.uploadFile(file, fileKey);

                Map<String, String> response = new HashMap<>();
                response.put("message", "File uploaded successfully");
                response.put("key", fileKey);

                return ResponseEntity.ok(response);
        }

        /**
         * DELETE /api/s3/delete/{key}
         */
        @DeleteMapping("/delete/{*key}")
        public ResponseEntity<Map<String, String>> deleteFile(@PathVariable String key) {

                s3Service.deleteFile(key);

                Map<String, String> response = new HashMap<>();
                response.put("message", "File deleted successfully");
                response.put("key", key);

                return ResponseEntity.ok(response);
        }

        /**
         * GET /api/s3/exists/{key}
         */
        @GetMapping("/exists/{*key}")
        public ResponseEntity<Map<String, Boolean>> fileExists(@PathVariable String key) {

                boolean exists = s3Service.fileExists(key);

                Map<String, Boolean> response = new HashMap<>();
                response.put("exists", exists);

                return ResponseEntity.ok(response);
        }

        /**
         * GET /api/s3/metadata/{key}
         */
        @GetMapping("/metadata/{*key}")
        public ResponseEntity<S3ObjectDTO> getFileMetadata(@PathVariable String key) {

                return ResponseEntity.ok(s3Service.getFileMetadata(key));
        }

        /**
         * POST /api/s3/copy
         */
        @PostMapping("/copy")
        public ResponseEntity<Map<String, String>> copyFile(
                        @RequestParam String sourceKey,
                        @RequestParam String destinationKey) {

                s3Service.copyFile(sourceKey, destinationKey);

                Map<String, String> response = new HashMap<>();
                response.put("message", "File copied successfully");
                response.put("sourceKey", sourceKey);
                response.put("destinationKey", destinationKey);

                return ResponseEntity.ok(response);
        }
}
