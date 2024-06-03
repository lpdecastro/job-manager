package com.lpdecastro.jobmanager.api;

import com.lpdecastro.jobmanager.dto.JobApplicationDto;
import com.lpdecastro.jobmanager.dto.JobApplicationsResponseDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;
import com.lpdecastro.jobmanager.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/.rest/job-applications")
@RequiredArgsConstructor
public class JobApplicationApi {

    private final JobApplicationService jobApplicationService;

    @GetMapping("/job-listings/{jobListingId}")
    public JobApplicationsResponseDto getJobApplicationsByJobListingId(@PathVariable long jobListingId) {
        return jobApplicationService.getJobApplicationsByJobListingId(jobListingId);
    }

    @GetMapping("/candidates/{candidateId}")
    public JobApplicationsResponseDto getJobApplicationsByCandidateId(@PathVariable long candidateId) {
        return jobApplicationService.getJobApplicationsByCandidateId(candidateId);
    }

    @GetMapping("/{jobListingId}")
    public JobApplicationDto getJobApplicationById(@PathVariable long jobListingId) {
        return jobApplicationService.getJobApplicationById(jobListingId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_CANDIDATE', 'SCOPE_ADMIN')")
    public JobApplicationDto createJobApplication(@RequestParam("jobListingId") Long jobListingId,
                                                  @RequestParam("candidateId") Long candidateId,
                                                  @RequestParam(value = "coverLetter", required = false)
                                                  MultipartFile coverLetter) {
        return jobApplicationService.createJobApplication(jobListingId, candidateId, coverLetter);
    }

    @PutMapping("/{jobApplicationId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_CANDIDATE', 'SCOPE_ADMIN')")
    public JobApplicationDto updateJobApplication(@PathVariable long jobApplicationId,
                                                  @RequestParam(value = "coverLetter", required = false)
                                                  MultipartFile coverLetter) {
        return jobApplicationService.updateJobApplication(jobApplicationId, coverLetter);
    }

    @DeleteMapping("/{jobApplicationId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_CANDIDATE', 'SCOPE_ADMIN')")
    public ResponseDto deleteJobApplication(@PathVariable long jobApplicationId) {
        return jobApplicationService.deleteJobApplication(jobApplicationId);
    }
}
