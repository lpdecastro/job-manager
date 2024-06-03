package com.lpdecastro.jobmanager.dto;

import com.lpdecastro.jobmanager.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecruiterDto {

    private long recruiterId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String company;
    private String city;
    private String province;
    private String zipCode;
    private String photo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
}
