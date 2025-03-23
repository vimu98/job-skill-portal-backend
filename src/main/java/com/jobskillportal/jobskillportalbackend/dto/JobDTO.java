package com.jobskillportal.jobskillportalbackend.dto;

import lombok.Data;

@Data
public class JobDTO {
    private String title;
    private String company;
    private String location;
    private String description;
    private String skillsRequired;
    private boolean active;
}
