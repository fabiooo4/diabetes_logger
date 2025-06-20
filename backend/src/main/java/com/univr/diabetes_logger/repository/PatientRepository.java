package com.univr.diabetes_logger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.univr.diabetes_logger.model.Patient;

// This will AUTO IMPLEMENT CRUD by Spring into a Bean called patientRepository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
