package com.lpdecastro.jobmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CandidateRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    private String firstName;
    private String lastName;
    private String company;
    private String city;
    private String province;
    private String zipCode;
    private List<SkillRequestDto> skills;
}
