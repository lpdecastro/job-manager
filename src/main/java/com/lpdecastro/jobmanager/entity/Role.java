package com.lpdecastro.jobmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleName name;

    @Column(name = "description")
    private String description;
}
