package com.univr.diabetes_logger.service;

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
  public Iterable<Patient> getAllPatients() {
    return repository.findAll();
  }

  @Override
  public Optional<Patient> getPatientById(Integer id) {
    return repository.findById(id);
  }

  @Override
  public Patient createPatient(Patient patient) {
    if (repository.findByEmail(patient.getEmail()).isPresent()) {
      throw new RuntimeException("Patient already exists");
    }

    return repository.save(patient);
  }

  @Override
  public Patient updatePatient(Integer id, Patient patient) {
    Patient existingPatient = this.getPatientById(id).orElseThrow();

    existingPatient.setFirstName(patient.getFirstName());
    existingPatient.setLastName(patient.getLastName());
    existingPatient.setAge(patient.getAge());
    existingPatient.setEmail(patient.getEmail());

    return repository.save(existingPatient);
  }

  @Override
  public Patient deletePatient(Integer id) {
    Patient deleted = this.getPatientById(id).orElseThrow();

    repository.deleteById(id);

    return deleted;
  }
}
