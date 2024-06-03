package com.lpdecastro.jobmanager.repository;

import com.lpdecastro.jobmanager.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    Optional<Recruiter> findByUser_Username(String username);
}
