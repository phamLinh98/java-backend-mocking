package com.example.java_backend.mappers;

import org.springframework.stereotype.Component;

import com.example.java_backend.DTO.S3ObjectDTO;

import software.amazon.awssdk.services.s3.model.S3Object;

@Component
public class S3ObjectMapper {

        public S3ObjectDTO toDTO(S3Object s3Object) {
                return new S3ObjectDTO(
                                s3Object.key(),
                                s3Object.size(),
                                s3Object.lastModified(),
                                s3Object.eTag(),
                                s3Object.storageClassAsString());
        }
}
