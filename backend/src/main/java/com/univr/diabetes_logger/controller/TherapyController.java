package com.univr.diabetes_logger.controller;

import com.univr.diabetes_logger.model.Therapy;
import com.univr.diabetes_logger.service.TherapyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * TherapyController
 */
@RestController
@RequestMapping(path = "/therapies")
public class TherapyController {

  @Autowired
  private TherapyService therapyService;

  public TherapyController(TherapyService therapyService) {
    this.therapyService = therapyService;
  }

  @GetMapping
  public Iterable<Therapy> getAllTherapies() {
    return therapyService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Therapy> getTherapyById(@PathVariable Integer id) {
    Optional<Therapy> therapy = therapyService.getById(id);

    return therapy.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<?> createTherapy(@RequestBody Therapy therapy, UriComponentsBuilder uriBuilder) {
    Therapy created = therapyService.create(therapy);

    Iterable<Therapy> therapies = therapyService.getAll();
    for (Therapy t : therapies) {
      if(t.getMedicine().equals(therapy.getMedicine()) && t.getDailyIntake().equals(therapy.getDailyIntake())
      && t.getDirections().equals(therapy.getDirections()) && t.getAmount().equals(therapy.getAmount())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Therapy is already registered");
      }
    }

    var uri = uriBuilder.path("/therapies/{id}").buildAndExpand(created.getId()).toUri();
    return ResponseEntity.created(uri).body(created);
  }

  @PutMapping("/{id}")
  public Therapy updateTherapy(@PathVariable Integer id, @RequestBody Therapy therapy,
      UriComponentsBuilder uriBuilder) {
    return therapyService.update(id, therapy);
  }

  @DeleteMapping("/{id}")
  public Therapy deleteTherapy(@PathVariable Integer id) {
    return therapyService.delete(id);
  }
}
