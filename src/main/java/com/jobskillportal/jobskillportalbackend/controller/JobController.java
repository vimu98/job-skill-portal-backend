package com.jobskillportal.jobskillportalbackend.controller;

import com.jobskillportal.jobskillportalbackend.dto.JobDTO;
import com.jobskillportal.jobskillportalbackend.dto.ResumeUrlDTO;
import com.jobskillportal.jobskillportalbackend.entity.Job;
import com.jobskillportal.jobskillportalbackend.service.JobMatchingService;
import com.jobskillportal.jobskillportalbackend.service.JobService;
import com.jobskillportal.jobskillportalbackend.service.ResumeParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5176", allowCredentials = "true")
@RestController
@RequestMapping("/api/jobs")
  // Allow frontend access
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JobMatchingService jobMatchingService;


    // Create Job
    @PostMapping("/create")
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        return ResponseEntity.ok(jobService.createJob(jobDTO));
    }

    // Get all Jobs
    @GetMapping("/all")
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    // Update Job
    @PutMapping("/update/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody JobDTO jobDTO) {
        JobDTO updatedJob = jobService.updateJob(id, jobDTO);
        return updatedJob != null ? ResponseEntity.ok(updatedJob) : ResponseEntity.notFound().build();
    }

    // Delete Job
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/match-jobs")
    public ResponseEntity<List<Job>> matchJobs(@RequestBody ResumeUrlDTO resumeUrlDTO) {
        try {

            String parsedText = ResumeParserService.parseResume(resumeUrlDTO);
            List<Job> matchingJobs = jobMatchingService.findMatchingJobs(parsedText);
            return ResponseEntity.ok(matchingJobs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
        JobDTO jobDTO = jobService.getJobById(id);
        return jobDTO != null ? ResponseEntity.ok(jobDTO) : ResponseEntity.notFound().build();
    }


}
