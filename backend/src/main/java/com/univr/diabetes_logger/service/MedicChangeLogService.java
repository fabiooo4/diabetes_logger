package com.univr.diabetes_logger.service;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.MedicChangeLog;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.Report;
import com.univr.diabetes_logger.repository.MedicChangeLogRepository;
import com.univr.diabetes_logger.repository.MedicRepository;
import com.univr.diabetes_logger.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicChangeLogService implements CrudService<MedicChangeLog> {
    private final MedicChangeLogRepository medicChangeLogRepository;
    private final MedicRepository medicRepository;
    private final PatientRepository patientRepository;

    public MedicChangeLogService(MedicChangeLogRepository medicChangeLogRepository, MedicRepository medicRepository, PatientRepository patientRepository) {
        this.medicChangeLogRepository = medicChangeLogRepository;
        this.medicRepository = medicRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public MedicChangeLog create(MedicChangeLog medicChangeLog) {
        return medicChangeLogRepository.save(medicChangeLog);
    }

    @Override
    public Iterable<MedicChangeLog> getAll() {
        return medicChangeLogRepository.findAll();
    }

    // Gettare tutti i cambiamenti che ha effettuato uno specifico medico
    public Iterable<MedicChangeLog> getAllByMedic(Integer medic_id) {
        return medicChangeLogRepository
                .findAll()
                .stream()
                .filter(medicChangeLog -> medicChangeLog.getMedic().getId().equals(medic_id))
                .collect(Collectors.toList());
    }

    // Gettare tutti i cambiamenti che paziente x ha subitos
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
