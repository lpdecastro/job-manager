package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.*;
import com.lpdecastro.jobmanager.entity.*;
import com.lpdecastro.jobmanager.exception.AppException;
import com.lpdecastro.jobmanager.repository.CompanyRepository;
import com.lpdecastro.jobmanager.repository.JobListingRepository;
import com.lpdecastro.jobmanager.repository.JobLocationRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

import static com.lpdecastro.jobmanager.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class JobListingServiceImpl implements JobListingService {

    private final JobListingRepository jobListingRepository;
    private final CompanyRepository companyRepository;
    private final JobLocationRepository jobLocationRepository;
    private final ModelMapper modelMapper;

    @Override
    public JobListingsResponseDto getJobListings(String title, String location, List<JobType> types,
                                                 List<JobRemote> remotes) {
        List<JobListingDto> jobListings;
        if (hasNoConditions(title, location, types, remotes)) {
            jobListings = jobListingRepository.findAll().stream()
                    .map(this::convertToDto)
                    .toList();
        } else {
            List<String> typeStrings = convertToTypeStrings(types);
            List<String> remoteStrings = convertToRemoteStrings(remotes);
            jobListings = jobListingRepository.search(title, location, typeStrings, remoteStrings).stream()
                    .map(this::convertToDto)
                    .toList();
        }

        return new JobListingsResponseDto(jobListings);
    }

    @Override
    public JobListingsResponseDto getJobListingsByCompanyId(long companyId) {
        List<JobListingDto> jobListings = jobListingRepository.findByCompany_CompanyId(companyId).stream()
                .map(this::convertToDto)
                .toList();
        return new JobListingsResponseDto(jobListings);
    }

    @Override
    public JobListingDto getJobListingById(long jobListingId) {
        return jobListingRepository.findById(jobListingId)
                .map(this::convertToDto)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, JOB_LISTING_NOT_FOUND));
    }

    @Override
    public JobListingDto createJobListing(CreateJobListingRequestDto createJobListingRequestDto) {
        JobListing jobListing = convertToEntity(createJobListingRequestDto);

        setCompanyAndJobLocation(createJobListingRequestDto.getCompanyId(),
                createJobListingRequestDto.getJobLocationId(),
                jobListing);

        return convertToDto(jobListingRepository.save(jobListing));
    }

    @Override
    public JobListingDto updateJobListing(long jobListingId, UpdateJobListingRequestDto updateJobListingRequestDto) {
        JobListing currentJobListing = jobListingRepository.findById(jobListingId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, JOB_LISTING_NOT_FOUND));

        currentJobListing.setTitle(updateJobListingRequestDto.getTitle());
        currentJobListing.setDescription(updateJobListingRequestDto.getDescription());
        currentJobListing.setType(updateJobListingRequestDto.getType());
        currentJobListing.setRemote(updateJobListingRequestDto.getRemote());
        currentJobListing.setSalary(updateJobListingRequestDto.getSalary());

        return convertToDto(jobListingRepository.save(currentJobListing));
    }

    @Override
    public ResponseDto deleteJobListing(long jobListingId) {
        JobListing currentJobListing = jobListingRepository.findById(jobListingId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, JOB_LISTING_NOT_FOUND));
        jobListingRepository.delete(currentJobListing);
        return new ResponseDto(true);
    }

    private boolean hasNoConditions(String title, String location, List<JobType> types, List<JobRemote> remotes) {
        return StringUtils.isBlank(title) && StringUtils.isBlank(location) && CollectionUtils.isEmpty(types) &&
                CollectionUtils.isEmpty(remotes);
    }

    private List<String> convertToTypeStrings(List<JobType> types) {
        // if no type selected, then include all type values in the query condition
        return CollectionUtils.isEmpty(types) ?
                Arrays.stream(JobType.values()).map(Enum::name).toList() :
                types.stream().map(Enum::name).toList();
    }

    private List<String> convertToRemoteStrings(List<JobRemote> remotes) {
        // if no remote selected, then include all remote values in the query condition
        return CollectionUtils.isEmpty(remotes) ?
                Arrays.stream(JobRemote.values()).map(Enum::name).toList() :
                remotes.stream().map(Enum::name).toList();
    }

    private void setCompanyAndJobLocation(Long companyId, Long jobLocationId, JobListing jobListing) {
        if (companyId == null) {
            return;
        }

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, JOB_COMPANY_NOT_FOUND));
        jobListing.setCompany(company);

        if (jobLocationId == null) {
            return;
        }

        JobLocation jobLocation = jobLocationRepository.findByJobLocationIdAndCompany_CompanyId(companyId,
                        jobLocationId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, JOB_LOCATION_NOT_FOUND));
        jobListing.setJobLocation(jobLocation);
    }

    private JobListingDto convertToDto(JobListing jobListing) {
        return modelMapper.map(jobListing, JobListingDto.class);
    }

    private JobListing convertToEntity(CreateJobListingRequestDto createJobListingRequestDto) {
        return modelMapper.map(createJobListingRequestDto, JobListing.class);
    }
}
