package com.univr.diabetes_logger.service;

import java.util.Optional;

import com.univr.diabetes_logger.model.Medic;

/**
 * MedicService
 */
public interface MedicService {
  Iterable<Medic> getAllMedics();

  Optional<Medic> getMedicById(Integer id);

  Medic createMedic(Medic medic);

  Medic updateMedic(Integer id, Medic medic);

  Medic deleteMedic(Integer id);
}
