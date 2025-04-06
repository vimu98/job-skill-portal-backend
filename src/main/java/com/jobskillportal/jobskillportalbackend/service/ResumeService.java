package com.jobskillportal.jobskillportalbackend.service;

import com.jobskillportal.jobskillportalbackend.dto.ResumeDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ResumeService {
    ResumeDTO saveResume(ResumeDTO resumeDTO);

    Optional<ResumeDTO> getResumeById(Long id);

    List<ResumeDTO> getResumesByUserId(Long userId);

    void deleteResume(Long id);

    ResumeDTO updateResume(ResumeDTO resumeDTO);
}
