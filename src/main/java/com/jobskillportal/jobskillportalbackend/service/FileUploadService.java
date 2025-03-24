package com.jobskillportal.jobskillportalbackend.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public interface FileUploadService {
    String uploadFile(MultipartFile file) throws IOException;
}
