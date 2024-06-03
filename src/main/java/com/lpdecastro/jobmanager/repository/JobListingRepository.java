package com.lpdecastro.jobmanager.repository;

import com.lpdecastro.jobmanager.entity.JobListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobListingRepository extends JpaRepository<JobListing, Long> {

    @Query(value = "select j.* from job_listings j " +
            "inner join job_locations l on l.job_location_id = j.job_location_id " +
            "where j.title like %:title% " +
            "and (l.city like %:location% or l.province like %:location% or l.country like %:location%) " +
            "and j.type in (:types) " +
            "and j.remote in (:remotes)", nativeQuery = true)
    List<JobListing> search(@Param("title") String title,
                            @Param("location") String location,
                            @Param("types") List<String> types,
                            @Param("remotes") List<String> remotes);

    List<JobListing> findByCompany_CompanyId(long companyId);

    void deleteByCompany_CompanyId(long companyId);
}
