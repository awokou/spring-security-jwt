package com.server.spring.security.jwt.repository;

import java.util.Optional;

import com.server.spring.security.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    // Since email is unique, we'll find users by email
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
