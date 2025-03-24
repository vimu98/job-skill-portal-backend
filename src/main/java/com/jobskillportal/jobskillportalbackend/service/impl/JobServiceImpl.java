package com.jobskillportal.jobskillportalbackend.service.impl;

import com.jobskillportal.jobskillportalbackend.dto.JobDTO;
import com.jobskillportal.jobskillportalbackend.entity.Job;
import com.jobskillportal.jobskillportalbackend.repo.JobRepository;
import com.jobskillportal.jobskillportalbackend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;


    private Job convertToEntity(JobDTO jobDTO) {
        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setCompany(jobDTO.getCompany());
        job.setLocation(jobDTO.getLocation());
        job.setDescription(jobDTO.getDescription());
        job.setIndustry(jobDTO.getIndustry());
        job.setSalary(jobDTO.getSalary());
        job.setSkillsRequired(jobDTO.getSkillsRequired());
        job.setExperienceRequired(jobDTO.getExperienceRequired());
        job.setActive(jobDTO.isActive());
        return job;
    }

    private JobDTO convertToDTO(Job job) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setTitle(job.getTitle());
        jobDTO.setCompany(job.getCompany());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setIndustry(job.getIndustry());
        jobDTO.setSalary(job.getSalary());
        jobDTO.setSkillsRequired(job.getSkillsRequired());
        jobDTO.setExperienceRequired(job.getExperienceRequired());
        jobDTO.setActive(job.isActive());
        return jobDTO;
    }


    public JobDTO createJob(JobDTO jobDTO) {
        Job job = convertToEntity(jobDTO);
        Job savedJob = jobRepository.save(job);
        return convertToDTO(savedJob);
    }


    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    public JobDTO updateJob(Long id, JobDTO jobDTO) {
        Optional<Job> existingJob = jobRepository.findById(id);
        if (existingJob.isPresent()) {
            Job job = existingJob.get();
            job.setTitle(jobDTO.getTitle());
            job.setCompany(jobDTO.getCompany());
            job.setLocation(jobDTO.getLocation());
            job.setDescription(jobDTO.getDescription());
            job.setIndustry(jobDTO.getIndustry());
            job.setSalary(jobDTO.getSalary());
            job.setSkillsRequired(jobDTO.getSkillsRequired());
            job.setExperienceRequired(jobDTO.getExperienceRequired());
            job.setActive(jobDTO.isActive());
            Job updatedJob = jobRepository.save(job);
            return convertToDTO(updatedJob);
        }
        return null;
    }


    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}
