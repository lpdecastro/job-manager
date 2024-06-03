package com.lpdecastro.jobmanager.service;

import com.lpdecastro.jobmanager.dto.ResponseDto;
import com.lpdecastro.jobmanager.dto.SavedJobDto;
import com.lpdecastro.jobmanager.dto.SavedJobRequestDto;
import com.lpdecastro.jobmanager.dto.SavedJobsResponseDto;
import com.lpdecastro.jobmanager.entity.Candidate;
import com.lpdecastro.jobmanager.entity.JobListing;
import com.lpdecastro.jobmanager.entity.SavedJob;
import com.lpdecastro.jobmanager.exception.AppException;
import com.lpdecastro.jobmanager.repository.CandidateRepository;
import com.lpdecastro.jobmanager.repository.JobListingRepository;
import com.lpdecastro.jobmanager.repository.SavedJobRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.lpdecastro.jobmanager.config.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class SavedJobServiceImpl implements SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final CandidateRepository candidateRepository;
    private final JobListingRepository jobListingRepository;
    private final ModelMapper modelMapper;

    @Override
    public SavedJobsResponseDto getSavedJobs() {
        Candidate currentCandidate = getCurrentCandidate();
        List<SavedJobDto> savedJobs = savedJobRepository.findByCandidate_CandidateId(currentCandidate.getCandidateId())
                .stream()
                .map(this::convertToDto)
                .toList();
        return new SavedJobsResponseDto(savedJobs);
    }

    @Override
    public SavedJobDto addToSavedJobs(SavedJobRequestDto savedJobRequestDto) {
        Candidate currentCandidate = getCurrentCandidate();
        JobListing currentJobListing = jobListingRepository.findById(savedJobRequestDto.getJobListingId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, JOB_LISTING_NOT_FOUND));

        validateSavedJob(currentCandidate.getCandidateId(), savedJobRequestDto.getJobListingId());

        SavedJob savedJob = new SavedJob();
        savedJob.setCandidate(currentCandidate);
        savedJob.setJobListing(currentJobListing);

        return convertToDto(savedJobRepository.save(savedJob));
    }

    @Override
    public ResponseDto removeFromSavedJobs(long savedJobId) {
        SavedJob savedJob = savedJobRepository.findById(savedJobId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, SAVED_JOB_NOT_FOUND));
        savedJobRepository.delete(savedJob);
        return new ResponseDto(true);
    }

    private Candidate getCurrentCandidate() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return candidateRepository.findByUser_Username(username)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, CANDIDATE_NOT_FOUND));
    }

    private void validateSavedJob(long candidateId, long jobListingId) {
        if (savedJobRepository.existsByCandidate_CandidateIdAndJobListing_JobListingId(candidateId, jobListingId)) {
            throw new AppException(HttpStatus.CONFLICT, JOB_ALREADY_SAVED);
        }
    }

    private SavedJobDto convertToDto(SavedJob savedJob) {
        return modelMapper.map(savedJob, SavedJobDto.class);
    }
}
