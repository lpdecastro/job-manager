package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.CompaniesResponseDto;
import com.lpdecastro.jobmanager.dto.CompanyDto;
import com.lpdecastro.jobmanager.dto.CompanyRequestDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;
import com.lpdecastro.jobmanager.entity.Company;
import com.lpdecastro.jobmanager.exception.AppException;
import com.lpdecastro.jobmanager.repository.CompanyRepository;
import com.lpdecastro.jobmanager.repository.JobListingRepository;
import com.lpdecastro.jobmanager.repository.JobLocationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lpdecastro.jobmanager.config.ErrorMessage.COMPANY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final JobListingRepository jobListingRepository;
    private final JobLocationRepository jobLocationRepository;
    private final ModelMapper modelMapper;

    @Override
    public CompaniesResponseDto getCompanies() {
        List<CompanyDto> companies = companyRepository.findAll().stream().map(this::convertToDto).toList();
        return new CompaniesResponseDto(companies);
    }

    @Override
    public CompanyDto getCompanyById(long companyId) {
        return companyRepository.findById(companyId).map(this::convertToDto)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, COMPANY_NOT_FOUND));
    }

    @Override
    public CompanyDto createCompany(CompanyRequestDto companyRequestDto) {
        Company company = convertToEntity(companyRequestDto);
        company.getJobLocations().forEach(location -> location.setCompany(company));
        return convertToDto(companyRepository.save(company));
    }

    @Override
    public CompanyDto updateCompany(long companyId, CompanyRequestDto companyRequestDto) {
        Company currentCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, COMPANY_NOT_FOUND));

        Company company = convertToEntity(companyRequestDto);
        company.setCompanyId(currentCompany.getCompanyId());
        company.getJobLocations().forEach(location -> location.setCompany(company));

        return convertToDto(companyRepository.save(company));
    }

    @Transactional
    @Override
    public ResponseDto deleteCompany(long companyId) {
        Company currentCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, COMPANY_NOT_FOUND));

        jobListingRepository.deleteByCompany_CompanyId(companyId);
        jobLocationRepository.deleteByCompany_CompanyId(companyId);
        companyRepository.delete(currentCompany);

        return new ResponseDto(true);
    }

    private CompanyDto convertToDto(Company company) {
        return modelMapper.map(company, CompanyDto.class);
    }

    private Company convertToEntity(CompanyRequestDto companyRequestDto) {
        return modelMapper.map(companyRequestDto, Company.class);
    }
}
