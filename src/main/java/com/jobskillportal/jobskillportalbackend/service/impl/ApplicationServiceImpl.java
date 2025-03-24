package com.jobskillportal.jobskillportalbackend.service.impl;

import com.jobskillportal.jobskillportalbackend.dto.ApplicationDTO;
import com.jobskillportal.jobskillportalbackend.entity.Application;
import com.jobskillportal.jobskillportalbackend.repo.ApplicationRepository;
import com.jobskillportal.jobskillportalbackend.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public ApplicationDTO createApplication(ApplicationDTO applicationDTO) {
        Application application = new Application();
        application.setJobId(applicationDTO.getJobId());
        application.setApplicantId(applicationDTO.getApplicantId());
        application.setResumeUrl(applicationDTO.getResumeUrl());
        application.setApplicationStatus(applicationDTO.getApplicationStatus());
        application.setAppliedDate(LocalDate.now()); // Set current date
        Application savedApplication = applicationRepository.save(application);

        // Return the saved application data as DTO
        applicationDTO.setApplicationId(savedApplication.getApplicationId());
        applicationDTO.setAppliedDate(savedApplication.getAppliedDate());
        return applicationDTO;
    }
}
