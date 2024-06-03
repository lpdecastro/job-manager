package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.JobApplicationDto;
import com.lpdecastro.jobmanager.dto.JobApplicationsResponseDto;
import com.lpdecastro.jobmanager.dto.ResponseDto;
import com.lpdecastro.jobmanager.entity.Candidate;
import com.lpdecastro.jobmanager.entity.JobApplication;
import com.lpdecastro.jobmanager.entity.JobListing;
import com.lpdecastro.jobmanager.exception.AppException;
import com.lpdecastro.jobmanager.repository.CandidateRepository;
import com.lpdecastro.jobmanager.repository.JobApplicationRepository;
import com.lpdecastro.jobmanager.repository.JobListingRepository;
import com.lpdecastro.jobmanager.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.lpdecastro.jobmanager.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    @Value("${app.data.candidates-directory}")
    private String candidatesDirectory;

    private final JobApplicationRepository jobApplicationRepository;
    private final JobListingRepository jobListingRepository;
    private final CandidateRepository candidateRepository;
    private final ModelMapper modelMapper;

    @Override
    public JobApplicationsResponseDto getJobApplicationsByJobListingId(long jobListingId) {
        List<JobApplicationDto> jobApplications = jobApplicationRepository.findByJobListing_JobListingId(jobListingId)
                .stream()
                .map(this::convertToDto)
                .toList();
        return new JobApplicationsResponseDto(jobApplications);
    }

    @Override
    public JobApplicationsResponseDto getJobApplicationsByCandidateId(long candidateId) {
        List<JobApplicationDto> jobApplications = jobApplicationRepository.findByCandidate_CandidateId(candidateId)
                .stream()
                .map(this::convertToDto)
                .toList();
        return new JobApplicationsResponseDto(jobApplications);
    }

    @Override
    public JobApplicationDto getJobApplicationById(long jobApplicationId) {
        return jobApplicationRepository.findById(jobApplicationId)
                .map(this::convertToDto)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, JOB_APPLICATION_NOT_FOUND));
    }

    @Override
    public JobApplicationDto createJobApplication(long jobListingId, long candidateId, MultipartFile coverLetter) {
        JobListing jobListing = jobListingRepository.findById(jobListingId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, JOB_LISTING_NOT_FOUND));

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CANDIDATE_NOT_FOUND));

        if (jobApplicationRepository.existsByJobListing_JobListingIdAndCandidate_CandidateId(jobListingId, candidateId)) {
            throw  new AppException(HttpStatus.CONFLICT, JOB_APPLICATION_ALREADY_EXISTS);
        }

        String uploadDir = candidatesDirectory + candidateId;
        String coverLetterFilename = StringUtils.cleanPath(Objects.requireNonNull(coverLetter.getOriginalFilename()));
        try {
            FileUploadUtil.saveFile(uploadDir, coverLetterFilename, coverLetter);
        } catch (IOException e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, FAILED_TO_UPLOAD_COVER_LETTER);
        }

        JobApplication jobApplication = new JobApplication();
        jobApplication.setCoverLetter(coverLetterFilename);
        jobApplication.setJobListing(jobListing);
        jobApplication.setCandidate(candidate);

        return convertToDto(jobApplicationRepository.save(jobApplication));
    }

    @Override
    public JobApplicationDto updateJobApplication(long jobApplicationId, MultipartFile coverLetter) {
        JobApplication currentJobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, JOB_APPLICATION_NOT_FOUND));

        String uploadDir = candidatesDirectory + currentJobApplication.getCandidate().getCandidateId();
        String coverLetterFilename = StringUtils.cleanPath(Objects.requireNonNull(coverLetter.getOriginalFilename()));
        try {
            FileUploadUtil.saveFile(uploadDir, coverLetterFilename, coverLetter);
        } catch (IOException e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, FAILED_TO_UPLOAD_COVER_LETTER);
        }

        currentJobApplication.setCoverLetter(coverLetterFilename);

        return convertToDto(jobApplicationRepository.save(currentJobApplication));
    }

    @Override
    public ResponseDto deleteJobApplication(long jobApplicationId) {
        JobApplication currentJobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, JOB_APPLICATION_NOT_FOUND));
        jobApplicationRepository.delete(currentJobApplication);
        return new ResponseDto(true);
    }

    private JobApplicationDto convertToDto(JobApplication jobApplication) {
        return modelMapper.map(jobApplication, JobApplicationDto.class);
    }
}
