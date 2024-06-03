package com.lpdecastro.jobmanager.dto;

import com.lpdecastro.jobmanager.entity.JobRemote;
import com.lpdecastro.jobmanager.entity.JobType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobListingDto {

    private long jobListingId;
    private String title;
    private String description;
    private JobType type;
    private JobRemote remote;
    private double salary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CompanyDto company;
    private JobLocationDto jobLocation;
}
