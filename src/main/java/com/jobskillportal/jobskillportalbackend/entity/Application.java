package com.jobskillportal.jobskillportalbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    private Long jobId;

    private Long applicantId;

    private String resumeUrl;

    private String applicationStatus;

    private LocalDate appliedDate;

    @PrePersist
    public void setAppliedDate() {
        if (this.appliedDate == null) {
            this.appliedDate = LocalDate.now();
        }
    }
}
