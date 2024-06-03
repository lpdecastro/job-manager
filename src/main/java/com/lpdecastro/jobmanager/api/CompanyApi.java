package com.lpdecastro.jobmanager.api;

import com.lpdecastro.jobmanager.dto.CompaniesResponseDto;
import com.lpdecastro.jobmanager.dto.CompanyDto;
import com.lpdecastro.jobmanager.dto.CompanyRequestDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;
import com.lpdecastro.jobmanager.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/.rest/companies")
@RequiredArgsConstructor
public class CompanyApi {

    private final CompanyService companyService;

    @GetMapping
    public CompaniesResponseDto getCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping("/{companyId}")
    public CompanyDto getCompanyById(@PathVariable long companyId) {
        return companyService.getCompanyById(companyId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_RECRUITER', 'SCOPE_ADMIN')")
    public CompanyDto createCompany(@Valid @RequestBody CompanyRequestDto companyRequestDto) {
        return companyService.createCompany(companyRequestDto);
    }

    @PutMapping("/{companyId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_RECRUITER', 'SCOPE_ADMIN')")
    public CompanyDto updateCompany(@PathVariable long companyId,
                                    @Valid @RequestBody CompanyRequestDto companyRequestDto) {
        return companyService.updateCompany(companyId, companyRequestDto);
    }

    @DeleteMapping("/{companyId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_RECRUITER', 'SCOPE_ADMIN')")
    public ResponseDto deleteCompany(@PathVariable long companyId) {
        return companyService.deleteCompany(companyId);
    }
}
