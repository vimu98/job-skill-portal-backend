package com.jobskillportal.jobskillportalbackend.controller;

import com.jobskillportal.jobskillportalbackend.dto.ResumeDTO;
import com.jobskillportal.jobskillportalbackend.service.FileUploadService;
import com.jobskillportal.jobskillportalbackend.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping
    public ResponseEntity<ResumeDTO> uploadResume(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("userId") Long userId) throws IOException {

        String fileUrl = fileUploadService.uploadFile(file);

        ResumeDTO resumeDTO = new ResumeDTO();
        resumeDTO.setUserId(userId);
        resumeDTO.setResumeUrl(fileUrl);

        return ResponseEntity.ok(resumeService.saveResume(resumeDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeDTO> getResumeById(@PathVariable Long id) {
        Optional<ResumeDTO> resume = resumeService.getResumeById(id);
        return resume.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResumeDTO>> getResumesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(resumeService.getResumesByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long id) {
        resumeService.deleteResume(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResumeDTO> updateResume(@PathVariable Long id,
                                                  @RequestParam(value = "file", required = false) MultipartFile file,
                                                  @RequestParam(value = "resumeUrl", required = false) String resumeUrl) throws IOException {

        Optional<ResumeDTO> existingResume = resumeService.getResumeById(id);
        if (existingResume.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ResumeDTO resumeDTO = existingResume.get();

        // If a new file is uploaded, update the resume URL
        if (file != null) {
            String newFileUrl = fileUploadService.uploadFile(file);
            resumeDTO.setResumeUrl(newFileUrl);
        } else if (resumeUrl != null) {
            resumeDTO.setResumeUrl(resumeUrl);
        }

        // Update the existing resume
        ResumeDTO updatedResume = resumeService.updateResume(resumeDTO);

        return ResponseEntity.ok(updatedResume);
    }

}
