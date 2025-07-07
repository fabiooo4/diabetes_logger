package com.univr.diabetes_logger.service;

import com.univr.diabetes_logger.model.Therapy;
import com.univr.diabetes_logger.repository.TherapyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * TherapyService
 */
@Service
public class TherapyService implements CrudService<Therapy> {

  private TherapyRepository therapyRepository;

  public TherapyService(TherapyRepository therapyRepository) {
    this.therapyRepository = therapyRepository;
  }

  @Override
  public Therapy create(Therapy therapy) {
    return therapyRepository.save(therapy);
  }

  @Override
  public Iterable<Therapy> getAll() {
    return therapyRepository.findAll();
  }

  @Override
  public Optional<Therapy> getById(Integer id) {
    return therapyRepository.findById(id);
  }

  @Override
  public Therapy update(Integer id, Therapy therapy) {
    Therapy existingTherapy = this.getById(id).orElseThrow();

    existingTherapy.setMedicine(therapy.getMedicine());
    existingTherapy.setDailyIntake(therapy.getDailyIntake());
    existingTherapy.setAmount(therapy.getAmount());
    existingTherapy.setDirections(therapy.getDirections());

    return therapyRepository.save(existingTherapy);
  }

  @Override
  public Therapy delete(Integer id) {
    Therapy deletedTherapy = this.getById(id).orElseThrow();

    therapyRepository.delete(deletedTherapy);

    return deletedTherapy;
  }
}
