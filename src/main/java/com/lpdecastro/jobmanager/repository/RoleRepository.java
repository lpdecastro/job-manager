package com.lpdecastro.jobmanager.repository;

import com.lpdecastro.jobmanager.entity.Role;
import com.lpdecastro.jobmanager.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
