package com.lpdecastro.jobmanager.repository;

import com.lpdecastro.jobmanager.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByUser_Username(String username);
}
