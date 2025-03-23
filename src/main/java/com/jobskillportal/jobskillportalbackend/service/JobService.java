package com.jobskillportal.jobskillportalbackend.service;

import com.jobskillportal.jobskillportalbackend.dto.JobDTO;
import com.jobskillportal.jobskillportalbackend.entity.Job;

import java.util.List;

public interface JobService {

    JobDTO createJob(JobDTO jobDTO);

    List<JobDTO> getAllJobs();

    JobDTO updateJob(Long id, JobDTO jobDTO);

    void deleteJob(Long id);
}
