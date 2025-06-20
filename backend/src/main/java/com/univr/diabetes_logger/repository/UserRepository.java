package com.univr.diabetes_logger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.univr.diabetes_logger.model.User;

import java.util.Optional;

// This will AUTO IMPLEMENT CRUD by Spring into a Bean called patientRepository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
}
