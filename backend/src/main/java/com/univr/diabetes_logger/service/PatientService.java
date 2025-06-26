package com.univr.diabetes_logger.service;

import java.util.Optional;

import com.univr.diabetes_logger.model.Therapy;
import com.univr.diabetes_logger.repository.TherapyRepository;
import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.repository.PatientRepository;

/**
 * PatientServiceImpl
 */
@Service
public class PatientService implements CrudService<Patient> {

  private TherapyRepository therapyRepository;
  private PatientRepository repository;



  public PatientService(PatientRepository repository, TherapyRepository therapyRepository) {
    this.repository = repository;
    this.therapyRepository = therapyRepository;
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

    existingPatient.setFirstName(patient.getFirstName());
    existingPatient.setLastName(patient.getLastName());
    existingPatient.setBirthDate(patient.getBirthDate());
    existingPatient.setReferralMedic(patient.getReferralMedic());
    existingPatient.setTherapy(patient.getTherapy());

    return repository.save(existingPatient);
  }

  @Override
  public Patient delete(Integer id) {
    Patient deleted = this.getById(id).orElseThrow();

    repository.deleteById(id);

    return deleted;
  }
}
