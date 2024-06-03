package com.lpdecastro.jobmanager.repository;

import com.lpdecastro.jobmanager.entity.JobLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobLocationRepository extends JpaRepository<JobLocation, Long> {

    Optional<JobLocation> findByJobLocationIdAndCompany_CompanyId(long jobLocationId, long companyId);
    void deleteByCompany_CompanyId(long companyId);
}
