package com.lpdecastro.jobmanager.repository;

import com.lpdecastro.jobmanager.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {

    List<SavedJob> findByCandidate_CandidateId(long candidateId);
    boolean existsByCandidate_CandidateIdAndJobListing_JobListingId(long candidateId, long jobListingId);
}
