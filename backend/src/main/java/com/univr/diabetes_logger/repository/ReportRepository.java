package com.univr.diabetes_logger.repository;
import com.univr.diabetes_logger.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    Optional<Report> findById(Integer id);
}