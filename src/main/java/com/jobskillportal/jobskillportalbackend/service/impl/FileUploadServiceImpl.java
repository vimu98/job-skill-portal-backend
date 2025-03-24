package com.jobskillportal.jobskillportalbackend.service.impl;

import com.jobskillportal.jobskillportalbackend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Method to upload file
    public String uploadFile(MultipartFile file) throws IOException {
        // Create directory if not exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Get the original filename
        String fileName = file.getOriginalFilename();

        // Create the path to store the file
        Path path = Paths.get(uploadDir, fileName);

        // Write the file to the path
        Files.write(path, file.getBytes());

        // Return the file URL (you can also return the local path for simplicity)
        return path.toString();  // e.g., "./uploads/resume.pdf"
    }
}
