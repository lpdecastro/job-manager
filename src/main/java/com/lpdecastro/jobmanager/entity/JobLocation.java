package com.lpdecastro.jobmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "job_locations")
@Data
public class JobLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_location_id")
    private long jobLocationId;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "country", length = 2)
    private String country;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
