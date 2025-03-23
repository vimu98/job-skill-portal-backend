package com.jobskillportal.jobskillportalbackend.controller;

import com.jobskillportal.jobskillportalbackend.dto.JobDTO;
import com.jobskillportal.jobskillportalbackend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@CrossOrigin("*")  // Allow frontend access
public class JobController {

    @Autowired
    private JobService jobService;

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
}
