package com.lpdecastro.jobmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationsResponseDto {

    private int total;
    private List<JobApplicationDto> jobApplications;

    public JobApplicationsResponseDto(List<JobApplicationDto> jobApplications) {
        this(jobApplications.size(), jobApplications);
    }
}
