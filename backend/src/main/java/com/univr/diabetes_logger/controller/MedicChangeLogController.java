package com.univr.diabetes_logger.controller;

import java.util.Optional;

import com.univr.diabetes_logger.model.MedicChangeLog;
import com.univr.diabetes_logger.service.MedicChangeLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * MedicChangeLogController
 */
@RestController
@RequestMapping(path = "/medicChangeLog")
public class MedicChangeLogController {
    private MedicChangeLogService medicChangeLogService;

    public MedicChangeLogController(MedicChangeLogService medicChangeLogService) {
        this.medicChangeLogService = medicChangeLogService;
    }

    @GetMapping
    public Iterable<MedicChangeLog> getAllChangeLogs() {
        return medicChangeLogService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicChangeLog> getChangeLogById(@PathVariable Integer id) {
        Optional<MedicChangeLog> medicChangeLog = medicChangeLogService.getById(id);

        if (medicChangeLog.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(medicChangeLog.get());
    }

    @GetMapping("/medic/{medicId}")
    public Iterable<MedicChangeLog> getChangeLogByMedicId(@PathVariable Integer medicId) {
        return medicChangeLogService.getAllByMedic(medicId);
    }

    @GetMapping("/patient/{patientId}")
    public MedicChangeLog getChangeLogByPatientId(@PathVariable Integer patientId) {
        return medicChangeLogService.getMostRecentLogByPatient(patientId);
    }

    @PostMapping
    public ResponseEntity<MedicChangeLog> createMedicChangeLog(@RequestBody MedicChangeLog medicChangeLog,
               UriComponentsBuilder uriBuilder) {
        MedicChangeLog created = medicChangeLogService.create(medicChangeLog);

        var uri = uriBuilder.path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public MedicChangeLog updateChangeLog(@PathVariable Integer id, @RequestBody MedicChangeLog medicChangeLog) {
        return medicChangeLogService.update(id, medicChangeLog);
    }

    @DeleteMapping("/{id}")
    public MedicChangeLog delete(@PathVariable Integer id) {
        return medicChangeLogService.delete(id);
    }
}
