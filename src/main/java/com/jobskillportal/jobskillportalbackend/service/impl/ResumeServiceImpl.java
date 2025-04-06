package com.jobskillportal.jobskillportalbackend.service.impl;

import com.jobskillportal.jobskillportalbackend.dto.ResumeDTO;
import com.jobskillportal.jobskillportalbackend.entity.Resume;
import com.jobskillportal.jobskillportalbackend.repo.ResumeRepository;
import com.jobskillportal.jobskillportalbackend.service.ResumeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResumeDTO saveResume(ResumeDTO resumeDTO) {
        Resume resume = new Resume();
        resume.setUserId(resumeDTO.getUserId());
        resume.setResumeUrl(resumeDTO.getResumeUrl());

        Resume savedResume = resumeRepository.save(resume);
        resumeDTO.setId(savedResume.getId());
        return resumeDTO;
    }

    public List<ResumeDTO> getResumesByUserId(Long userId) {
        return resumeRepository.findByUserId(userId).stream()
                .map(resume -> new ResumeDTO(resume.getId(), resume.getUserId(), resume.getResumeUrl()))
                .collect(Collectors.toList());
    }

    public Optional<ResumeDTO> getResumeById(Long id) {
        return resumeRepository.findById(id)
                .map(resume -> new ResumeDTO(resume.getId(), resume.getUserId(), resume.getResumeUrl()));
    }

    public void deleteResume(Long id) {
        resumeRepository.deleteById(id);
    }

    public ResumeDTO updateResume(ResumeDTO resumeDTO) {
        // Ensure resume exists in DB
        Optional<Resume> existingResume = resumeRepository.findById(resumeDTO.getId());
        if (existingResume.isEmpty()) {
            throw new RuntimeException("Resume not found!");
        }

        Resume resume = existingResume.get();

        // Update fields
        resume.setResumeUrl(resumeDTO.getResumeUrl());

        Resume updatedResume = resumeRepository.save(resume);
        return modelMapper.map(updatedResume, ResumeDTO.class);
    }

}
