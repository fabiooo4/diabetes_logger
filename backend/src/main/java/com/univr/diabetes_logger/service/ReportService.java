package com.univr.diabetes_logger.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.Report;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.repository.NotificationRepository;
import com.univr.diabetes_logger.repository.ReportRepository;
import com.univr.diabetes_logger.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MedicServiceImpl
 */
@Service
public class ReportService implements CrudService<Report> {
  private final ReportRepository repository;
  private final UserRepository userRepository;

  @Autowired
  private final NotificationService notificationService;

  public ReportService(ReportRepository repository, UserRepository userRepository,
      NotificationService notificationService) {
    this.repository = repository;
    this.userRepository = userRepository;
    this.notificationService = notificationService;
  }

  @Transactional
  @Override
  public Report create(Report report) {
    checkLevels(report);
    return repository.save(report);
  }

  private void checkLevels(Report report) {
    int glycemia = report.getGlycemiaLevel();
    String fullName = report.getPatient().getFirstName() + " " + report.getPatient().getLastName();
    if (report.getBeforeMeal()) {
      if (glycemia < 80 || glycemia > 130) {
        if (glycemia < 50 || glycemia > 170) {
          notifyCritical(fullName, glycemia);
        } else {
          notifyWarning(fullName, glycemia);
        }
      }
    } else {
      if (glycemia > 180) {
        if (glycemia > 210) {
          notifyCritical(fullName, glycemia);
        } else {
          notifyWarning(fullName, glycemia);
        }
      }
    }
  }

  private void notifyCritical(String fullName, int glycemia) {
    notificationService.NotifyAllMedics(
        "ATTENTION, " + fullName +
            " has surpassed the glycemia level threshold with a level of " +
            glycemia + ", URGENT CHECK");
  }

  private void notifyWarning(String fullName, int glycemia) {
    notificationService.NotifyAllMedics(
        fullName + " has surpassed the glycemia " +
            "level threshold with a level of " + glycemia);
  }

  public Optional<Report> createOnUser(Report report, Integer id) {
    Optional<User> user = userRepository.findById(id);

    if (user.isEmpty()) {
      return Optional.empty();
    }

    if (user.get().getPatient() == null) {
      return Optional.empty();
    }

    report.setPatient(user.get().getPatient());
    checkLevels(report);
    return Optional.of(repository.save(report));
  }

  @Override
  public Iterable<Report> getAll() {
    return repository.findAll();
  }

  @Override
  public Optional<Report> getById(Integer id) {
    return repository.findById(id);
  }

  public List<Report> getAllByUserId(Integer userId) {

    List<Report> listOfReports = repository.findAll();

    return listOfReports.stream().filter(report -> {

      Patient patient = report.getPatient();
      if (patient == null) {
        return false;
      }

      User user = patient.getUser();
      if (user == null) {
        return false;
      }

      return user.getId().equals(userId);
    }

    ).collect(Collectors.toList());
  }

  @Override
  public Report update(Integer id, Report report) {
    Report existingReport = this.getById(id).orElseThrow();

    Integer glycemiaLevel = report.getGlycemiaLevel();
    if (glycemiaLevel != null) {
      existingReport.setGlycemiaLevel(glycemiaLevel);
    }

    Boolean beforeMeal = report.getBeforeMeal();
    if (beforeMeal != null) {
      existingReport.setBeforeMeal(beforeMeal);
    }

    String symptoms = report.getSymptoms();
    if (symptoms != null) {
      existingReport.setSymptoms(symptoms);
    }

    String notes = report.getNotes();
    if (notes != null) {
      existingReport.setNotes(notes);
    }

    LocalDateTime dateTime = report.getDateTime();
    if (dateTime != null) {
      existingReport.setDateTime(dateTime);
    }

    String medicine = report.getMedicine();
    if (medicine != null) {
      existingReport.setMedicine(medicine);
    }

    Integer amount = report.getAmount();
    if (amount != null) {
      existingReport.setAmount(amount);
    }

    Patient patient = report.getPatient();
    if (patient != null) {
      existingReport.setPatient(patient);
    }

    return repository.save(existingReport);
  }

  public Report updateOnUser(Integer id, Report report) {
    Report existingReport = this.getById(id).orElseThrow();

    // TODO: Modify the update
    existingReport.setGlycemiaLevel(report.getGlycemiaLevel());
    existingReport.setBeforeMeal(report.getBeforeMeal());
    existingReport.setSymptoms(report.getSymptoms());
    existingReport.setNotes(report.getNotes());
    existingReport.setDateTime(report.getDateTime());
    existingReport.setMedicine(report.getMedicine());
    existingReport.setAmount(report.getAmount());
    // Cannot change patient

    return repository.save(existingReport);
  }

  @Override
  public Report delete(Integer id) {
    Report deleted = this.getById(id).orElseThrow();

    repository.deleteById(id);

    return deleted;
  }
}
