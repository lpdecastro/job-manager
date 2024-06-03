package com.lpdecastro.jobmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CompanyRequestDto {

    @NotBlank
    private String name;

    private String logo;

    @NotNull
    @Size(min = 1)
    private List<JobLocationDto> jobLocations;
}
