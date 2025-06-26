package com.univr.diabetes_logger.repository;

import com.univr.diabetes_logger.model.Therapy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TherapyRepository extends JpaRepository<Therapy, Integer> {
    Optional<Therapy> findById(Integer id);
}
