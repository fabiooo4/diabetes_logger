package com.univr.diabetes_logger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.univr.diabetes_logger.model.Medic;

// This will AUTO IMPLEMENT CRUD by Spring into a Bean called medicRepository
public interface MedicRepository extends JpaRepository<Medic, Integer> {
}
