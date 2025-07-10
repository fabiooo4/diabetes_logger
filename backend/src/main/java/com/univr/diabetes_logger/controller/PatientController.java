package com.univr.diabetes_logger.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.service.PatientService;

/**
 * PatientController
 */
@RestController
@RequestMapping(path = "/patients")
public class PatientController {
  @Autowired
  private PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping
  public Iterable<Patient> getAllPatients() {
    return patientService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Patient> getPatientById(@PathVariable Integer id) {
    Optional<Patient> patient = patientService.getById(id);

    if (patient.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(patient.get());
  }

  @PostMapping
  public ResponseEntity<?> createPatient(@RequestBody Patient patient, UriComponentsBuilder uriBuilder) {
    Patient created = patientService.create(patient);

    if (patient.getTherapy() != null) {

      if (patient.getTherapy().getMedicine() == null || patient.getTherapy().getMedicine().isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medicine is required");
      }

      if (patient.getTherapy().getAmount() == null || patient.getTherapy().getAmount() < 0) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Amount is required or amount is negative");
      }

      if (patient.getTherapy().getDailyIntake() == null || patient.getTherapy().getDailyIntake() < 0) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("dailyIntake is required or is negative");
      }

      if (patient.getTherapy().getDirections() == null || patient.getTherapy().getDirections().isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Directions is required");
      }
    }
    var uri = uriBuilder.path("/patients/{id}").buildAndExpand(created.getId()).toUri();
    return ResponseEntity.created(uri).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePatient(@PathVariable Integer id, @RequestBody Patient patient) {

    if (patient.getTherapy() != null) {

      if (patient.getTherapy().getMedicine() == null || patient.getTherapy().getMedicine().isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medicine is required");
      }

      if (patient.getTherapy().getAmount() == null || patient.getTherapy().getAmount() < 0) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Amount is required or amount is negative");
      }

      if (patient.getTherapy().getDailyIntake() == null || patient.getTherapy().getDailyIntake() < 0) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("dailyIntake is required or is negative");
      }

      if (patient.getTherapy().getDirections() == null || patient.getTherapy().getDirections().isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Directions is required");
      }
    }

    return ResponseEntity.ok(patientService.update(id, patient));
  }

  @PutMapping("/medic/{medicId}/{patientId}")
  public ResponseEntity<?> updatePatient(@PathVariable Integer medicId, @PathVariable Integer patientId,
      @RequestBody Patient patient) {

    if (patient.getTherapy() != null) {

      if (patient.getTherapy().getMedicine() == null || patient.getTherapy().getMedicine().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medicine is required");
      }

      if (patient.getTherapy().getAmount() == null || patient.getTherapy().getAmount() < 0) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount is required or amount is negative");
      }

      if (patient.getTherapy().getDailyIntake() == null || patient.getTherapy().getDailyIntake() < 0) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("dailyIntake is required or is negative");
      }

      if (patient.getTherapy().getDirections() == null || patient.getTherapy().getDirections().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Directions is required");
      }
    }

    return ResponseEntity.ok(patientService.medicUpdate(medicId, patientId, patient));
  }

  @DeleteMapping("/{id}")
  public Patient deletePatient(@PathVariable Integer id) {
    return patientService.delete(id);
  }
}
