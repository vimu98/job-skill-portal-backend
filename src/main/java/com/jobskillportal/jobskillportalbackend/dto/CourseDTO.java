package com.jobskillportal.jobskillportalbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private String title;
    private String description;
    private String category;
    private String duration;
    private String skillLevel;
}