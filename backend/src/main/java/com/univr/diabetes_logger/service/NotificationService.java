package com.univr.diabetes_logger.service;

import com.univr.diabetes_logger.model.*;
import com.univr.diabetes_logger.repository.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * NotificationService
 */
@Service
public class NotificationService implements CrudService<Notification> {

  private final NotificationRepository notificationRepository;
  private final ReportRepository reportRepository;
  private final UserRepository userRepository;
  private final PatientRepository patientRepository;
  private final MedicRepository medicRepository;

  public NotificationService(NotificationRepository notificationRepository, ReportRepository reportRepository,
      UserRepository userRepository, PatientRepository patientRepository, MedicRepository medicRepository) {
    this.notificationRepository = notificationRepository;
    this.reportRepository = reportRepository;
    this.userRepository = userRepository;
    this.patientRepository = patientRepository;
    this.medicRepository = medicRepository;
  }

  @Override
  public Notification create(Notification notification) {
    return notificationRepository.save(notification);
  }

  public Optional<Notification> createOnUser(Notification notification, Integer id) {
    Optional<User> user = userRepository.findById(id);

    if (user.isEmpty()) {
      return Optional.empty();
    }

    notification.setUser(user.get());
    return Optional.of(notificationRepository.save(notification));
  }

  @Override
  public Iterable<Notification> getAll() {
    return notificationRepository.findAll();
  }

  @Override
  public Optional<Notification> getById(Integer id) {
    return notificationRepository.findById(id);
  }

  public List<Notification> getAllByUserId(Integer userId) {
    List<Notification> listOfReports = notificationRepository.findAll();
    return listOfReports.stream().filter(notif -> {

      User user = notif.getUser();
      if (user == null) {
        return false;
      }

      return user.getId().equals(userId);
    }

    ).collect(Collectors.toList());
  }

  @Override
  public Notification update(Integer id, Notification notification) {
    Notification existingNotif = this.getById(id).orElseThrow();

    existingNotif.setCreatedAt(notification.getCreatedAt());
    existingNotif.setMessage(notification.getMessage());
    existingNotif.setSeen(notification.isSeen());
    existingNotif.setUser(notification.getUser());

    return notificationRepository.save(existingNotif);
  }

  public Notification updateOnUser(Integer id, Notification notification) {
    Notification existingNotif = this.getById(id).orElseThrow();

    existingNotif.setCreatedAt(notification.getCreatedAt());
    existingNotif.setMessage(notification.getMessage());
    existingNotif.setUser(notification.getUser());
    existingNotif.setSeen(notification.isSeen());

    return notificationRepository.save(existingNotif);
  }

  @Override
  public Notification delete(Integer id) {
    Notification deleted = this.getById(id).orElseThrow();

    notificationRepository.deleteById(id);

    return deleted;
  }

  // Run this every fixed amount of time
  @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 1)
  public void checkMissingReports() {
    List<Report> reports = reportRepository.findAll();
    List<Patient> patients = patientRepository.findAll();

    for (Patient patient : patients) {
      List<Report> reportPerPatient = reports
          .stream()
          .filter(report -> patient.getId().equals(report.getPatient().getId()))
          .toList();

      Optional<Report> mostRecent = reportPerPatient.stream().max(Comparator.comparing(Report::getDateTime));

      if (mostRecent.isPresent()) {
        if (mostRecent.get().getDateTime().isBefore(LocalDateTime.now().minusDays(3))) {
          notificationRepository.save(new Notification(patient.getFirstName() + " " + patient.getLastName() +
              " has not submitted a report in the last 3 days!", false, LocalDateTime.now(),
              patient.getReferralMedic().getUser()));
        }
      }
    }
  }

  @Scheduled(timeUnit = TimeUnit.HOURS, fixedRate = 8)
  public void remindPatients() {
    for (Patient patient : patientRepository.findAll()) {
      notificationRepository.save(new Notification("Remember to write your daily report!",
          false, LocalDateTime.now(), patient.getUser()));
    }
  }

  public void notifyAllMedics(String message) {
    for (Medic medic : medicRepository.findAll()) {
      notificationRepository.save(new Notification(message,
          false, LocalDateTime.now(), medic.getUser()));
    }
  }

  public Notification toggleSeen(Integer id) {
    Notification notif = getById(id).orElseThrow();
    notif.toggleSeen();

    return notificationRepository.save(notif);
  }

  public Notification setSeen(Integer id) {
    Notification notif = getById(id).orElseThrow();
    notif.setSeen(true);

    return notificationRepository.save(notif);
  }
}
