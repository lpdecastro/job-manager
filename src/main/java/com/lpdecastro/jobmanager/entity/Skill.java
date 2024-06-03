package com.lpdecastro.jobmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "skills")
@Data
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private long skillId;

    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "years_of_experience")
    private int yearsOfExperience;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level")
    private SkillLevel skillLevel;
}
