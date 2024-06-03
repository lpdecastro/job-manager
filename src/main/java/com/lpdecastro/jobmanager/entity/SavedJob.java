package com.lpdecastro.jobmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "saved_job")
@Data
public class SavedJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saved_job_id")
    private long savedJobId;

    @ManyToOne
    @JoinColumn(name = "job_listing_id")
    private JobListing jobListing;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
