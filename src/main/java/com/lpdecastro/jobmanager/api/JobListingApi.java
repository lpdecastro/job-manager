package com.lpdecastro.jobmanager.api;

import com.lpdecastro.jobmanager.dto.*;
import com.lpdecastro.jobmanager.entity.JobRemote;
import com.lpdecastro.jobmanager.entity.JobType;
import com.lpdecastro.jobmanager.service.JobListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/.rest/job-listings")
@RequiredArgsConstructor
public class JobListingApi {

    private final JobListingService jobListingService;

    @GetMapping
    public JobListingsResponseDto getJobListings(@RequestParam(value = "title", required = false) String title,
                                                 @RequestParam(value = "location", required = false) String location,
                                                 @RequestParam(value = "types", required = false) List<JobType> types,
                                                 @RequestParam(value = "remotes", required = false) List<JobRemote> remotes) {
        return jobListingService.getJobListings(title, location, types, remotes);
    }

    @GetMapping("/company/{companyId}")
    public JobListingsResponseDto getJobListingsByCompanyId(@PathVariable long companyId) {
        return jobListingService.getJobListingsByCompanyId(companyId);
    }

    @GetMapping("/{jobListingId}")
    public JobListingDto getJobListingById(@PathVariable long jobListingId) {
        return jobListingService.getJobListingById(jobListingId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_RECRUITER', 'SCOPE_ADMIN')")
    public JobListingDto createJobListing(@Valid @RequestBody CreateJobListingRequestDto createJobListingRequestDto) {
        return jobListingService.createJobListing(createJobListingRequestDto);
    }

    @PutMapping("/{jobListingId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_RECRUITER', 'SCOPE_ADMIN')")
    public JobListingDto updateJobListing(@PathVariable long jobListingId,
                                          @Valid @RequestBody UpdateJobListingRequestDto updateJobListingRequestDto) {
        return jobListingService.updateJobListing(jobListingId, updateJobListingRequestDto);
    }

    @DeleteMapping("/{jobListingId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_RECRUITER', 'SCOPE_ADMIN')")
    public ResponseDto deleteJobListing(@PathVariable long jobListingId) {
        return jobListingService.deleteJobListing(jobListingId);
    }
}
