package com.univr.diabetes_logger.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.MedicChangeLog;
import com.univr.diabetes_logger.repository.MedicChangeLogRepository;
import com.univr.diabetes_logger.repository.MedicRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.repository.PatientRepository;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * PatientService
 */
@Service
public class PatientService implements CrudService<Patient> {

  private PatientRepository repository;
  private MedicRepository medicRepository;
  private MedicChangeLogRepository medicChangeLogRepository;

  public PatientService(PatientRepository repository, MedicRepository medicRepository,
      MedicChangeLogRepository medicChangeLogRepository) {
    this.repository = repository;
    this.medicRepository = medicRepository;
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
  public Patient update(Integer id, Patient patient) throws IllegalArgumentException {
    Patient existingPatient = this.getById(id).orElseThrow();
    existingPatient.updatePatient(patient);

    return repository.save(existingPatient);
  }

  public Patient medicUpdate(Integer medicId, Integer patientId, Patient patient) throws IllegalArgumentException {
    Patient existingPatient = this.getById(patientId).orElseThrow(
        () -> new IllegalArgumentException("Patient not found"));
    Medic editingMedic = medicRepository.findById(medicId)
        .orElseThrow(() -> new IllegalArgumentException("Medic not found"));

    String actions = existingPatient.actionPerformed(patient);
    existingPatient.updatePatient(patient);

    if (actions != null) {
      medicChangeLogRepository.save(new MedicChangeLog(editingMedic,
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

  public ResponseEntity<?> checkPatient(Patient patient, UriComponentsBuilder uriBuilder) {

    if (patient.getTherapy() != null) {

      if (patient.getTherapy().getMedicine() == null || patient.getTherapy().getMedicine().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medicine is required");
      }

      if (patient.getTherapy().getAmount() == null || patient.getTherapy().getAmount() < 0) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount is required or amount is negative");
      }

      if (patient.getTherapy().getDailyIntake() == null || patient.getTherapy().getDailyIntake() < 0) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Daily intake is required or is negative");
      }

      if (patient.getTherapy().getDirections() == null || patient.getTherapy().getDirections().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Directions is required");
      }
    }

    var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();
    return ResponseEntity.created(uri).body(patient);
  }

}
