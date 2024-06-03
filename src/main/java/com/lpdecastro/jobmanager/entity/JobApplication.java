package com.lpdecastro.jobmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
@Data
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_application_id")
    private long jobApplicationId;

    @Column(name = "cover_letter")
    private String coverLetter;

    @CreationTimestamp
    @Column(name = "applied_date", updatable = false)
    private LocalDateTime appliedDate;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "job_listing_id")
    private JobListing jobListing;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
