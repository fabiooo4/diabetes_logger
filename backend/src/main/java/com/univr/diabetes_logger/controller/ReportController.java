package com.univr.diabetes_logger.controller;

import java.util.List;
import java.util.Optional;

import com.univr.diabetes_logger.model.Report;
import com.univr.diabetes_logger.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.web.bind.annotation.PutMapping;

/**
 * ReportController
 */
@RestController
@RequestMapping(path = "/reports")
public class ReportController {
  @Autowired
  private ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @GetMapping
  public Iterable<Report> getAllReports() {
    return reportService.getAll();
  }

  @GetMapping("/{id}")
  public Optional<Report> getReportById(@PathVariable Integer id) {
    return reportService.getById(id);
  }

  @GetMapping("user/{userId}/{id}")
  public Optional<Report> getUserReportById(@PathVariable Integer userId, @PathVariable Integer id) {
    List<Report> listOfReports = reportService.getAllByUserId(userId);

    return listOfReports.stream().filter(report -> id.equals(report.getId())).findFirst();
  }

  @GetMapping("user/{userId}")
  public Iterable<Report> getReportsByUserId(@PathVariable Integer userId) {
    return reportService.getAllByUserId(userId);
  }

  @GetMapping("patient/{patientId}/{id}")
  public Optional<Report> getPatientReportById(@PathVariable Integer patientId, @PathVariable Integer id) {
    List<Report> listOfReports = reportService.getAllByPatientId(patientId);

    return listOfReports.stream().filter(report -> id.equals(report.getId())).findFirst();
  }

  @GetMapping("patient/{patientId}")
  public Iterable<Report> getReportsByPatientId(@PathVariable Integer patientId) {
    return reportService.getAllByPatientId(patientId);
  }

  @PostMapping("user/{userId}")
  public ResponseEntity<?> createUserReport(@RequestBody Report report, @PathVariable Integer userId,
      UriComponentsBuilder uriBuilder) {
    if (report.getDateTime() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date and Time are required");
    }

    if (report.getGlycemiaLevel() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Glycemia Level is required");
    }

    if (report.getBeforeMeal() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Before Meal is required");
    }

    if (report.getMedicine() == null || report.getMedicine().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medicine is required");
    }

    if (report.getAmount() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount is required");
    }

    Report created = reportService.createOnUser(report, userId).orElseThrow();

    var uri = uriBuilder.path("/reports/user/{userId}/{id}").buildAndExpand(userId, created.getId()).toUri();
    return ResponseEntity.created(uri).body(created);
  }

  @PostMapping
  public ResponseEntity<?> createReport(@RequestBody Report report, UriComponentsBuilder uriBuilder) {
    if (report.getDateTime() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date and Time are required");
    }

    if (report.getGlycemiaLevel() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Glycemia Level is required");
    }

    if (report.getBeforeMeal() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Before Meal is required");
    }

    if (report.getMedicine() == null || report.getMedicine().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medicine is required");
    }

    if (report.getAmount() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount is required");
    }

    Report created = reportService.create(report);

    var uri = uriBuilder.path("/reports/{id}").buildAndExpand(created.getId()).toUri();
    return ResponseEntity.created(uri).body(created);
  }

  @PutMapping("user/{userId}/{id}")
  public ResponseEntity<?> updateUserReport(@RequestBody Report report, @PathVariable Integer id) {
    if (report.getDateTime() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date and Time are required");
    }

    if (report.getGlycemiaLevel() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Glycemia Level is required");
    }

    if (report.getBeforeMeal() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Before Meal is required");
    }

    if (report.getMedicine() == null || report.getMedicine().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medicine is required");
    }

    if (report.getAmount() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount is required");
    }

    return ResponseEntity.ok(reportService.updateOnUser(id, report));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateReport(@RequestBody Report report, @PathVariable Integer id) {

    if (report.getDateTime() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date and Time are required");
    }

    if (report.getGlycemiaLevel() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Glycemia Level is required");
    }

    if (report.getBeforeMeal() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Before Meal is required");
    }

    if (report.getMedicine() == null || report.getMedicine().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medicine is required");
    }

    if (report.getAmount() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount is required");
    }

    return ResponseEntity.ok(reportService.update(id, report));
  }

  @DeleteMapping("user/{userId}/{id}")
  public Report deleteUserReportById(@PathVariable Integer id) {
    return reportService.delete(id);
  }

  @DeleteMapping("/{id}")
  public Report deleteReportById(@PathVariable Integer id) {
    return reportService.delete(id);
  }

}
