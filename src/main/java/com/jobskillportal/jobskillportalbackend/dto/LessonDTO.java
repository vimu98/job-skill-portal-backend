package com.jobskillportal.jobskillportalbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private String title;
    private String content;
    private String videoUrl;
    private String fileUrl;
    private Long moduleId;
}
