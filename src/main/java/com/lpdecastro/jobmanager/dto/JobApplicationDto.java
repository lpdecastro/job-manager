package com.lpdecastro.jobmanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobApplicationDto {

    private long jobApplicationId;
    private LocalDateTime appliedDate;
    private String coverLetter;
    private JobListingDto jobListing;
    private CandidateDto candidate;
}
