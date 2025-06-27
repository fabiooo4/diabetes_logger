package com.univr.diabetes_logger.service;

import java.time.LocalDate;
import java.util.Optional;

import com.univr.diabetes_logger.model.Therapy;
import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.repository.PatientRepository;

/**
 * PatientServiceImpl
 */
@Service
public class PatientService implements CrudService<Patient> {

  private PatientRepository repository;

  public PatientService(PatientRepository repository) {
    this.repository = repository;
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

    String firstName = patient.getFirstName();
    if (firstName != null) {
      existingPatient.setFirstName(firstName);
    }

    String lastName = patient.getLastName();
    if (lastName != null) {
      existingPatient.setLastName(lastName);
    }

    LocalDate birthDate = patient.getBirthDate();
    if (birthDate != null) {
      existingPatient.setBirthDate(birthDate);
    }

    Medic referralMedic = patient.getReferralMedic();
    if (referralMedic != null) {
      existingPatient.setReferralMedic(referralMedic);
    }

    Therapy therapy = patient.getTherapy();
    if (therapy != null) {
      existingPatient.setTherapy(therapy);
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
