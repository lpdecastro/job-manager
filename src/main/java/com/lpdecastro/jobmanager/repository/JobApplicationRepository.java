package com.lpdecastro.jobmanager.repository;

import com.lpdecastro.jobmanager.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByJobListing_JobListingId(long jobApplicationId);
    List<JobApplication> findByCandidate_CandidateId(long candidateId);
    boolean existsByJobListing_JobListingIdAndCandidate_CandidateId(long jobApplicationId, long candidateId);
}
