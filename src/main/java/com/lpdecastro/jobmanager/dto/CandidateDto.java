package com.lpdecastro.jobmanager.dto;

import com.lpdecastro.jobmanager.entity.Role;
import com.lpdecastro.jobmanager.entity.Skill;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CandidateDto {

    private long candidateId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String company;
    private String city;
    private String province;
    private String zipCode;
    private String resume;
    private String photo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Skill> skills;
    private Role role;
}
