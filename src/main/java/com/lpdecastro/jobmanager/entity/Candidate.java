package com.lpdecastro.jobmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "candidates")
@Data
@NoArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private long candidateId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "company")
    private String company;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "resume")
    private String resume;

    @Column(name = "photo")
    private String photo;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "candidate_id")
    private List<Skill> skills;

    public Candidate(User user) {
        this.user = user;
    }
}
