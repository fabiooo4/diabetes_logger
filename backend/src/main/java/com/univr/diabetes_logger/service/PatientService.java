package com.univr.diabetes_logger.service;

import java.util.Optional;

import com.univr.diabetes_logger.model.Patient;

/**
 * PatientService
 */
public interface PatientService {
  Iterable<Patient> getAllPatients();

  Optional<Patient> getPatientById(Integer id);

  Patient createPatient(Patient patient);

  Patient updatePatient(Integer id, Patient patient);

  Patient deletePatient(Integer id);
}
