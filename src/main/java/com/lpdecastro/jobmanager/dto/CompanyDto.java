package com.lpdecastro.jobmanager.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompanyDto {

    private Long companyId;
    private String name;
    private String logo;
    private List<JobLocationDto> jobLocations;
}
