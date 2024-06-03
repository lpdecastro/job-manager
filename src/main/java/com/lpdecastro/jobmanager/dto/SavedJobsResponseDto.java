package com.lpdecastro.jobmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedJobsResponseDto {

    private int total;
    private List<SavedJobDto> savedJobs;

    public SavedJobsResponseDto(List<SavedJobDto> savedJobs) {
        this(savedJobs.size(), savedJobs);
    }
}
