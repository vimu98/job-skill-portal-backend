package com.jobskillportal.jobskillportalbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {

    private Long applicationId;
    private Long jobId;
    private Long applicantId;
    private String resumeUrl;
    private String applicationStatus;
    private LocalDate appliedDate;
}
