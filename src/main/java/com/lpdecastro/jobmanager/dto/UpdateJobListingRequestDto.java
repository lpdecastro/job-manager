package com.lpdecastro.jobmanager.dto;

import com.lpdecastro.jobmanager.entity.JobRemote;
import com.lpdecastro.jobmanager.entity.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateJobListingRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private JobType type;

    @NotNull
    private JobRemote remote;

    private Double salary;
}
