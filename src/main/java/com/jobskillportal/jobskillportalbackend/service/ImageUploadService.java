package com.jobskillportal.jobskillportalbackend.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    String uploadFile(MultipartFile profilePicture);
}
