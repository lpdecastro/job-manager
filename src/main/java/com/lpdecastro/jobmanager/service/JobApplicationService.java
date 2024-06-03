package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.JobApplicationDto;
import com.lpdecastro.jobmanager.dto.JobApplicationsResponseDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface JobApplicationService {

    JobApplicationsResponseDto getJobApplicationsByJobListingId(long jobListingId);
    JobApplicationsResponseDto getJobApplicationsByCandidateId(long candidateId);
    JobApplicationDto getJobApplicationById(long jobApplicationId);
    JobApplicationDto createJobApplication(long jobListingId, long candidateId, MultipartFile coverLetter);
    JobApplicationDto updateJobApplication(long jobApplicationId, MultipartFile coverLetter);
    ResponseDto deleteJobApplication(long jobApplicationId);
}
