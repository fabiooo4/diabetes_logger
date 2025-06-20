package com.univr.diabetes_logger.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.repository.MedicRepository;

/**
 * MedicServiceImpl
 */
@Service
public class MedicServiceImpl implements MedicService {
  private MedicRepository repository;

  public MedicServiceImpl(MedicRepository repository) {
    this.repository = repository;
  }

  @Override
  public Iterable<Medic> getAllMedics() {
    return repository.findAll();
  }

  @Override
  public Optional<Medic> getMedicById(Integer id) {
    return repository.findById(id);
  }

  @Override
  public Medic createMedic(Medic medic) {
    if (repository.findByEmail(medic.getEmail()).isPresent()) {
      throw new RuntimeException("Medic already exists");
    }

    return repository.save(medic);
  }

  @Override
  public Medic updateMedic(Integer id, Medic medic) {
    Medic existingMedic = this.getMedicById(id).orElseThrow();

    existingMedic.setFirstName(medic.getFirstName());
    existingMedic.setLastName(medic.getLastName());
    existingMedic.setEmail(medic.getEmail());

    return repository.save(existingMedic);
  }

  @Override
  public Medic deleteMedic(Integer id) {
    Medic deleted = this.getMedicById(id).orElseThrow();

    repository.deleteById(id);

    return deleted;
  }
}
