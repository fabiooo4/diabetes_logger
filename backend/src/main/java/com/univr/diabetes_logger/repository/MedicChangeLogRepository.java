package com.univr.diabetes_logger.repository;

import com.univr.diabetes_logger.model.MedicChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicChangeLogRepository extends JpaRepository<MedicChangeLog, Integer> {
    Optional<MedicChangeLog> findById(Integer id);
}
