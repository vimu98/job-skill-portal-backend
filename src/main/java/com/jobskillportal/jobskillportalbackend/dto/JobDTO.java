package com.jobskillportal.jobskillportalbackend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JobDTO {
    private String title;
    private String company;
    private String location;
    private String description;
    private String skillsRequired;
    private String experienceRequired;
    private String industry;
    private String salary;
    private boolean active;
    private LocalDate publishDate;
}
