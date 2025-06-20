package com.univr.diabetes_logger.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.repository.MedicRepository;

/**
 * MedicServiceImpl
 */
@Service
public class MedicService implements CrudService<Medic> {
  private MedicRepository repository;

  public MedicService(MedicRepository repository) {
    this.repository = repository;
  }

  @Override
  public Iterable<Medic> getAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Medic> getById(Integer id) {
    return repository.findById(id);
  }

  @Override
  public Medic create(Medic medic) {
    if (repository.findByEmail(medic.getEmail()).isPresent()) {
      throw new RuntimeException("Medic already exists");
    }

    return repository.save(medic);
  }

  @Override
  public Medic update(Integer id, Medic medic) {
    Medic existingMedic = this.getById(id).orElseThrow();

    existingMedic.setFirstName(medic.getFirstName());
    existingMedic.setLastName(medic.getLastName());
    existingMedic.setEmail(medic.getEmail());

    return repository.save(existingMedic);
  }

  @Override
  public Medic delete(Integer id) {
    Medic deleted = this.getById(id).orElseThrow();

    repository.deleteById(id);

    return deleted;
  }
}
