package com.lpdecastro.jobmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "companies")
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private long companyId;

    @Column(name = "name")
    private String name;

    @Column(name = "logo")
    private String logo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", orphanRemoval = true)
    private List<JobLocation> jobLocations;
}
