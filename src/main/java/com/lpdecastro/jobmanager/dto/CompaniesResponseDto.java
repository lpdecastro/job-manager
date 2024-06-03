package com.lpdecastro.jobmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompaniesResponseDto {

    private int total;
    private List<CompanyDto> companies;

    public CompaniesResponseDto(List<CompanyDto> companies) {
        this(companies.size(), companies);
    }
}
