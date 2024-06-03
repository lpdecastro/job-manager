package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.*;
import com.lpdecastro.jobmanager.entity.JobRemote;
import com.lpdecastro.jobmanager.entity.JobType;

import java.util.List;

public interface JobListingService {

    JobListingsResponseDto getJobListings(String title, String location, List<JobType> types, List<JobRemote> remotes);
    JobListingsResponseDto getJobListingsByCompanyId(long companyId);
    JobListingDto getJobListingById(long jobListingId);
    JobListingDto createJobListing(CreateJobListingRequestDto createJobListingRequestDto);
    JobListingDto updateJobListing(long jobListingId, UpdateJobListingRequestDto updateJobListingRequestDto);
    ResponseDto deleteJobListing(long jobListingId);
}
