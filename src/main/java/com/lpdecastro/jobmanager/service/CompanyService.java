package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.CompaniesResponseDto;
import com.lpdecastro.jobmanager.dto.CompanyDto;
import com.lpdecastro.jobmanager.dto.CompanyRequestDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;

public interface CompanyService {

    CompaniesResponseDto getCompanies();
    CompanyDto getCompanyById(long companyId);
    CompanyDto createCompany(CompanyRequestDto companyRequestDto);
    CompanyDto updateCompany(long companyId, CompanyRequestDto companyRequestDto);
    ResponseDto deleteCompany(long companyId);
}
