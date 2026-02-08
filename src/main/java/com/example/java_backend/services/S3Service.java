package com.example.java_backend.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.java_backend.DTO.S3DownloadResponse;
import com.example.java_backend.DTO.S3ListResponse;
import com.example.java_backend.DTO.S3ObjectDTO;
import com.example.java_backend.mappers.S3ObjectMapper;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3Service {

        private final S3Client s3Client;
        private final S3ObjectMapper s3ObjectMapper;

        @Value("${aws.s3.bucket-name}")
        private String bucketName;

        // ✅ Constructor Injection (CHUẨN)
        public S3Service(S3Client s3Client, S3ObjectMapper s3ObjectMapper) {
                this.s3Client = s3Client;
                this.s3ObjectMapper = s3ObjectMapper;
        }

        /**
         * Lấy danh sách tất cả object trong bucket
         */
        public S3ListResponse listObjects() {
                try {
                        ListObjectsV2Response response = s3Client.listObjectsV2(
                                        ListObjectsV2Request.builder()
                                                        .bucket(bucketName)
                                                        .build());

                        List<S3ObjectDTO> objects = response.contents().stream()
                                        .map(s3ObjectMapper::toDTO)
                                        .collect(Collectors.toList());

                        return new S3ListResponse(bucketName, objects, objects.size());

                } catch (S3Exception e) {
                        throw new RuntimeException(
                                        "Error listing objects from S3 [" +
                                                        e.awsErrorDetails().errorCode() + "]: " +
                                                        e.awsErrorDetails().errorMessage(),
                                        e);
                }
        }

        /**
         * Lấy danh sách object theo prefix
         */
        public S3ListResponse listObjectsByPrefix(String prefix) {
                try {
                        ListObjectsV2Response response = s3Client.listObjectsV2(
                                        ListObjectsV2Request.builder()
                                                        .bucket(bucketName)
                                                        .prefix(prefix)
                                                        .build());

                        List<S3ObjectDTO> objects = response.contents().stream()
                                        .map(s3ObjectMapper::toDTO)
                                        .collect(Collectors.toList());

                        return new S3ListResponse(bucketName, objects, objects.size());

                } catch (S3Exception e) {
                        throw new RuntimeException(
                                        "Error listing objects by prefix from S3 [" +
                                                        e.awsErrorDetails().errorCode() + "]: " +
                                                        e.awsErrorDetails().errorMessage(),
                                        e);
                }
        }

        /**
         * Download file
         */
        public S3DownloadResponse downloadFile(String key) {
                try (ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(GetObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .build())) {

                        GetObjectResponse response = s3Object.response();
                        byte[] content = s3Object.readAllBytes();

                        return new S3DownloadResponse(
                                        key,
                                        response.contentType(),
                                        response.contentLength(),
                                        content);

                } catch (IOException e) {
                        throw new RuntimeException("Error reading file content", e);
                } catch (S3Exception e) {
                        throw new RuntimeException("Error downloading file from S3", e);
                }
        }

        /**
         * Upload file
         */
        public String uploadFile(MultipartFile file, String key) {
                try {
                        s3Client.putObject(
                                        PutObjectRequest.builder()
                                                        .bucket(bucketName)
                                                        .key(key)
                                                        .contentType(file.getContentType())
                                                        .build(),
                                        RequestBody.fromBytes(file.getBytes()));

                        return "File uploaded successfully: " + key;

                } catch (IOException e) {
                        throw new RuntimeException("Error reading file", e);
                } catch (S3Exception e) {
                        throw new RuntimeException("Error uploading file to S3", e);
                }
        }

        /**
         * Delete file
         */
        public void deleteFile(String key) {
                s3Client.deleteObject(DeleteObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .build());
        }

        /**
         * Check file exists
         */
        public boolean fileExists(String key) {
                try {
                        s3Client.headObject(HeadObjectRequest.builder()
                                        .bucket(bucketName)
                                        .key(key)
                                        .build());
                        return true;
                } catch (NoSuchKeyException e) {
                        return false;
                }
        }

        /**
         * Get file metadata
         */
        public S3ObjectDTO getFileMetadata(String key) {
                HeadObjectResponse response = s3Client.headObject(
                                HeadObjectRequest.builder()
                                                .bucket(bucketName)
                                                .key(key)
                                                .build());

                return new S3ObjectDTO(
                                key,
                                response.contentLength(),
                                response.lastModified(),
                                response.eTag(),
                                response.storageClassAsString());
        }

        /**
         * Copy file
         */
        public void copyFile(String sourceKey, String destinationKey) {
                s3Client.copyObject(CopyObjectRequest.builder()
                                .sourceBucket(bucketName)
                                .sourceKey(sourceKey)
                                .destinationBucket(bucketName)
                                .destinationKey(destinationKey)
                                .build());
        }
}
