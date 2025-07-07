package com.univr.diabetes_logger.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.univr.diabetes_logger.model.MedicChangeLog;
import com.univr.diabetes_logger.repository.MedicChangeLogRepository;
import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.repository.PatientRepository;

/**
 * PatientService
 */
@Service
public class PatientService implements CrudService<Patient> {

  private PatientRepository repository;
  private MedicChangeLogRepository medicChangeLogRepository;

  public PatientService(PatientRepository repository, MedicChangeLogRepository medicChangeLogRepository) {
    this.repository = repository;
    this.medicChangeLogRepository = medicChangeLogRepository;
  }

  @Override
  public Iterable<Patient> getAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Patient> getById(Integer id) {
    return repository.findById(id);
  }

  @Override
  public Patient create(Patient patient) {
    return repository.save(patient);
  }

  @Override
  public Patient update(Integer id, Patient patient) {
    Patient existingPatient = this.getById(id).orElseThrow();
    String actions = existingPatient.actionPerformed(patient);
    existingPatient.updatePatient(patient);

    if (actions != null) {
      medicChangeLogRepository.save(new MedicChangeLog(existingPatient.getReferralMedic(),
          existingPatient, actions, LocalDateTime.now()));
    }

    return repository.save(existingPatient);
  }

  @Override
  public Patient delete(Integer id) {
    Patient deleted = this.getById(id).orElseThrow();

    repository.deleteById(id);

    return deleted;
  }
}
