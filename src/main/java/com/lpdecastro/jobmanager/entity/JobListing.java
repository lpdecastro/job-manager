package com.lpdecastro.jobmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_listings")
@Data
public class JobListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_listing_id")
    private long jobListingId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private JobType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "remote")
    private JobRemote remote;

    @Column(name = "salary")
    private double salary;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "job_location_id")
    private JobLocation jobLocation;
}
