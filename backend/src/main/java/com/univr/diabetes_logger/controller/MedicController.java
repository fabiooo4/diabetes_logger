package com.univr.diabetes_logger.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.service.MedicService;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * MedicController
 */
@RestController
@RequestMapping(path = "/medics")
public class MedicController {
  @Autowired
  private MedicService medicService;

  public MedicController(MedicService medicService) {
    this.medicService = medicService;
  }

  @GetMapping
  public Iterable<Medic> getAllMedics() {
    return medicService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Medic> getMedicById(@PathVariable Integer id) {
    Optional<Medic> medic = medicService.getById(id);

    if (medic.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(medic.get());
  }

  @PostMapping
  public ResponseEntity<Medic> createMedic(@RequestBody Medic medic, UriComponentsBuilder uriBuilder) {
    Medic created = medicService.create(medic);

    var uri = uriBuilder.path("/medics/{id}").buildAndExpand(created.getId()).toUri();
    return ResponseEntity.created(uri).body(created);
  }

  @PutMapping("/{id}")
  public Medic updateMedic(@PathVariable Integer id, @RequestBody Medic medic) {
    return medicService.update(id, medic);
  }

  @DeleteMapping("/{id}")
  public Medic deleteMedic(@PathVariable Integer id) {
    return medicService.delete(id);
  }
}
