package com.lpdecastro.jobmanager.dto;

import lombok.Data;

@Data
public class SavedJobDto {

    private long savedJobId;
    private JobListingDto jobListing;
    private CandidateDto candidate;
}
