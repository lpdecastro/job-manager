package com.lpdecastro.jobmanager.dto;

import com.lpdecastro.jobmanager.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private long userId;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
}
