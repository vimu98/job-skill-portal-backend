package com.jobskillportal.jobskillportalbackend.service.impl;

import com.jobskillportal.jobskillportalbackend.dto.ApplicationDTO;
import com.jobskillportal.jobskillportalbackend.entity.Application;
import com.jobskillportal.jobskillportalbackend.repo.ApplicationRepository;
import com.jobskillportal.jobskillportalbackend.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
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

    @Override
    public ApplicationDTO updateApplication(Long applicationId, ApplicationDTO applicationDTO) {
        // Find the application to update
        Application existingApplication = applicationRepository.findById(applicationId).orElse(null);
        if (existingApplication != null) {
            if(applicationDTO.getJobId() != null ) {
                existingApplication.setJobId(applicationDTO.getJobId());
                existingApplication.setApplicantId(applicationDTO.getApplicantId());
                existingApplication.setResumeUrl(applicationDTO.getResumeUrl());
                existingApplication.setApplicationStatus(applicationDTO.getApplicationStatus());
                existingApplication.setAppliedDate(applicationDTO.getAppliedDate());
            }
            existingApplication.setApplicationStatus(applicationDTO.getApplicationStatus());

            applicationRepository.save(existingApplication);
            // Return the updated application data as DTO
            applicationDTO.setApplicationId(existingApplication.getApplicationId());
            return applicationDTO;
        }
        return null; // Return null if not found
    }

    @Override
    public boolean deleteApplication(Long applicationId) {
        // Check if the application exists and delete
        if (applicationRepository.existsById(applicationId)) {
            applicationRepository.deleteById(applicationId);
            return true;
        }
        return false;
    }

    @Override
    public List<ApplicationDTO> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        List<ApplicationDTO> applicationDTOs = new ArrayList<>();
        for (Application application : applications) {
            ApplicationDTO applicationDTO = new ApplicationDTO(
                    application.getApplicationId(),
                    application.getJobId(),
                    application.getApplicantId(),
                    application.getResumeUrl(),
                    application.getApplicationStatus(),
                    application.getAppliedDate()
            );
            applicationDTOs.add(applicationDTO);
        }
        return applicationDTOs;
    }

    @Override
    public ApplicationDTO getApplicationById(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElse(null);
        if (application != null) {
            return new ApplicationDTO(
                    application.getApplicationId(),
                    application.getJobId(),
                    application.getApplicantId(),
                    application.getResumeUrl(),
                    application.getApplicationStatus(),
                    application.getAppliedDate()
            );
        }
        return null; // Return null if not found
    }

    @Override
    public List<ApplicationDTO> getApplicationsByUserId(Long userId) {
        List<Application> applications = applicationRepository.findByApplicantId(userId);
        List<ApplicationDTO> applicationDTOs = new ArrayList<>();
        for (Application application : applications) {
            ApplicationDTO applicationDTO = new ApplicationDTO(
                    application.getApplicationId(),
                    application.getJobId(),
                    application.getApplicantId(),
                    application.getResumeUrl(),
                    application.getApplicationStatus(),
                    application.getAppliedDate()
            );
            applicationDTOs.add(applicationDTO);
        }
        return applicationDTOs;
    }

    @Override
    public List<ApplicationDTO> getApplicationsByJobId(Long jobId) {
        List<Application> applications = applicationRepository.findByJobId(jobId);
        List<ApplicationDTO> applicationDTOs = new ArrayList<>();
        for (Application application : applications) {
            ApplicationDTO applicationDTO = new ApplicationDTO(
                    application.getApplicationId(),
                    application.getJobId(),
                    application.getApplicantId(),
                    application.getResumeUrl(),
                    application.getApplicationStatus(),
                    application.getAppliedDate()
            );
            applicationDTOs.add(applicationDTO);
        }
        return applicationDTOs;
    }

}
