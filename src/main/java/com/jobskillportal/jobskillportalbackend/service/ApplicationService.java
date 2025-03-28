package com.jobskillportal.jobskillportalbackend.service;

import com.jobskillportal.jobskillportalbackend.dto.ApplicationDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ApplicationService {
    ApplicationDTO createApplication(ApplicationDTO applicationDTO);

    ApplicationDTO updateApplication(Long applicationId, ApplicationDTO applicationDTO);

    boolean deleteApplication(Long applicationId);

    List<ApplicationDTO> getAllApplications();

    ApplicationDTO getApplicationById(Long applicationId);

    List<ApplicationDTO> getApplicationsByUserId(Long userId);

    List<ApplicationDTO> getApplicationsByJobId(Long jobId);
}
