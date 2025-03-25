package com.jobskillportal.jobskillportalbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobskillportal.jobskillportalbackend.entity.Job;
import com.jobskillportal.jobskillportalbackend.repo.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Arrays;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobMatchingService {

    @Autowired
    private JobRepository jobRepository; // Repository to fetch jobs



    public List<Job> findMatchingJobs(String extractedSkills) {
        if (extractedSkills == null || extractedSkills.trim().isEmpty()) {
            return List.of(); // Return empty list if no skills provided
        }

        String jsonResponse = extractedSkills;
        String content = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            content = rootNode.path("choices").get(0).path("message").path("content").asText();
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<String> skillList = Arrays.stream(content.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        System.out.println("hi....."+skillList);

        List<Job> allJobs = jobRepository.findAll();

        return allJobs.stream()
                .filter(job -> {
                    String jobSkills = job.getSkillsRequired().toLowerCase();
                    return skillList.stream().anyMatch(jobSkills::contains);
                })
                .collect(Collectors.toList());
    }

}
