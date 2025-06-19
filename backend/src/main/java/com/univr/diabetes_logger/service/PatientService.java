package com.univr.diabetes_logger.service;

import java.util.List;
import java.util.Optional;

import com.univr.diabetes_logger.model.Patient;

/**
 * PatientService
 */
public interface PatientService {
  List<Patient> getAllPatients();
  Optional<Patient> getPatientById(Integer id);
  Patient createPatient(Patient patient);
  
}
