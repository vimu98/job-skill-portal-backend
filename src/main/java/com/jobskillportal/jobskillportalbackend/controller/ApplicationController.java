package com.jobskillportal.jobskillportalbackend.controller;

import com.jobskillportal.jobskillportalbackend.dto.ApplicationDTO;
import com.jobskillportal.jobskillportalbackend.entity.Application;
import com.jobskillportal.jobskillportalbackend.service.ApplicationService;
import com.jobskillportal.jobskillportalbackend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<ApplicationDTO> uploadApplication(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobId") Long jobId,
            @RequestParam("applicantId") Long applicantId,
            @RequestParam("fileUrl") String fileUrl,
            @RequestParam("applicationStatus") String applicationStatus) throws IOException {

        // Upload the file and get the file URL
       // String fileUrl = fileUploadService.uploadFile(file);

        // Create an ApplicationDTO object and save it
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setJobId(jobId);
        applicationDTO.setApplicantId(applicantId);
        applicationDTO.setResumeUrl(fileUrl);
        applicationDTO.setApplicationStatus(applicationStatus);

        ApplicationDTO savedApplication = applicationService.createApplication(applicationDTO);

        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }
}
