package com.lpdecastro.jobmanager.repository;

import com.lpdecastro.jobmanager.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
