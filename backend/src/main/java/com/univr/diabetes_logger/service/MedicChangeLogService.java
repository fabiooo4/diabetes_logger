package com.univr.diabetes_logger.service;

import com.univr.diabetes_logger.model.MedicChangeLog;
import com.univr.diabetes_logger.repository.MedicChangeLogRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicChangeLogService implements CrudService<MedicChangeLog> {
  private final MedicChangeLogRepository medicChangeLogRepository;

  public MedicChangeLogService(MedicChangeLogRepository medicChangeLogRepository) {
    this.medicChangeLogRepository = medicChangeLogRepository;
  }

  @Override
  public MedicChangeLog create(MedicChangeLog medicChangeLog) {
    return medicChangeLogRepository.save(medicChangeLog);
  }

  @Override
  public Iterable<MedicChangeLog> getAll() {
    return medicChangeLogRepository.findAll();
  }

  // Get all changes made by a specific medic
  public Iterable<MedicChangeLog> getAllByMedic(Integer medic_id) {
    return medicChangeLogRepository
        .findAll()
        .stream()
        .filter(medicChangeLog -> medicChangeLog.getMedic().getId().equals(medic_id))
        .collect(Collectors.toList());
  }

  // Get all changes made to a specific patient
  public Iterable<MedicChangeLog> getAllByPatient(Integer patient_id) {
    return medicChangeLogRepository
        .findAll()
        .stream()
        .filter(medicChangeLog -> medicChangeLog.getPatient().getId().equals(patient_id))
        .collect(Collectors.toList());
  }

  public MedicChangeLog getMostRecentLogByPatient(Integer patient_id) {
    return medicChangeLogRepository
        .findAll()
        .stream()
        .filter(medicChangeLog -> medicChangeLog.getPatient().getId().equals(patient_id))
        .max(Comparator.comparing(MedicChangeLog::getTimestamp))
        .stream().findFirst().orElse(null);
  }

  @Override
  public Optional<MedicChangeLog> getById(Integer id) {
    return medicChangeLogRepository.findById(id);
  }

  @Override
  public MedicChangeLog update(Integer id, MedicChangeLog medicChangeLog) {
    MedicChangeLog mcl = medicChangeLogRepository.findById(id).orElseThrow();

    mcl.setMedic(medicChangeLog.getMedic());
    mcl.setPatient(medicChangeLog.getPatient());
    mcl.setAction(medicChangeLog.getAction());
    mcl.setTimestamp(medicChangeLog.getTimestamp());

    return medicChangeLogRepository.save(mcl);
  }

  @Override
  public MedicChangeLog delete(Integer id) {
    MedicChangeLog deleted = this.getById(id).orElseThrow();

    medicChangeLogRepository.deleteById(id);

    return deleted;
  }
}
