package com.lpdecastro.jobmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobListingsResponseDto {

    private int total;
    private List<JobListingDto> jobListings;

    public JobListingsResponseDto(List<JobListingDto> jobListings) {
        this(jobListings.size(), jobListings);
    }
}
