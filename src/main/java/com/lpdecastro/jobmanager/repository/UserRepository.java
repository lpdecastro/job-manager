package com.lpdecastro.jobmanager.repository;

import com.lpdecastro.jobmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameAndUserIdNot(String username, long userId);
    boolean existsByEmailAndUserIdNot(String email, long userId);
    Optional<User> findByUsername(String username);
}
