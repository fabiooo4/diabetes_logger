package com.univr.diabetes_logger.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.repository.PatientRepository;

/**
 * PatientServiceImpl
 */
@Service
public class PatientServiceImpl implements PatientService {
  private PatientRepository repository;

  public PatientServiceImpl(PatientRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Patient> getAllPatients() {
    return repository.findAll();
  }

  @Override
  public Optional<Patient> getPatientById(Integer id) {
    return repository.findById(id);
  }

  @Override
  public Patient createPatient(Patient patient) {
    return repository.save(patient);
  }

}
