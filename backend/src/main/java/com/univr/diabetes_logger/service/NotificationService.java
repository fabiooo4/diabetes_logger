package com.univr.diabetes_logger.service;

import com.univr.diabetes_logger.model.Notification;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.Report;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.repository.NotificationRepository;
import com.univr.diabetes_logger.repository.PatientRepository;
import com.univr.diabetes_logger.repository.ReportRepository;
import com.univr.diabetes_logger.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class NotificationService implements CrudService<Notification> {

  private final NotificationRepository notificationRepository;
  private final ReportRepository reportRepository;
  private final UserRepository userRepository;
  private final PatientRepository patientRepository;

  public NotificationService(NotificationRepository notificationRepository, ReportRepository reportRepository,
      UserRepository userRepository, PatientRepository patientRepository) {
    this.notificationRepository = notificationRepository;
    this.reportRepository = reportRepository;
    this.userRepository = userRepository;
    this.patientRepository = patientRepository;
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
    return null;
  }

  public Notification updateOnUser(Integer id, Notification notification) {
    Notification existingNotif = this.getById(id).orElseThrow();

    existingNotif.setCreatedAt(notification.getCreatedAt());
    existingNotif.setMessage(notification.getMessage());
    existingNotif.setUser(notification.getUser());
    // Cannot change the recipient

    return notificationRepository.save(existingNotif);
  }

  @Override
  public Notification delete(Integer id) {
    Notification deleted = this.getById(id).orElseThrow();

    notificationRepository.deleteById(id);

    return deleted;
  }

  // Run this every fixed amount of time
  @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 30)
  public void checkMissingReports() {
    List<Report> reports = reportRepository.findAll();
    List<Patient> patients = patientRepository.findAll();

    for (Patient patient : patients) {
      List<Report> reportPerPatient = reports
          .stream()
          .filter(report -> patient.getId().equals(report.getPatient().getId()))
          .toList();

      // Find the most recent report by date
      Optional<Report> mostRecent = reportPerPatient.stream().max(Comparator.comparing(Report::getDateTime));

      if (mostRecent.isPresent()) {
        if (mostRecent.get().getDateTime().isBefore(LocalDateTime.now().minusDays(3))) {
          notificationRepository.save(
              new Notification("Son passati più di 3 giorni da quando non scrivi una rilevazione.",
                  false, LocalDateTime.now(), patient.getUser()));
        }
      }
    }
  }

  public Notification toggleSeen(Integer id) {
    Notification notif = getById(id).orElseThrow();
    notif.toggleSeen();

    return notificationRepository.save(notif);
  }
}
