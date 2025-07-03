package com.univr.diabetes_logger.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.univr.diabetes_logger.model.*;
import com.univr.diabetes_logger.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.univr.diabetes_logger.model.User.Role;

@Configuration
public class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(UserRepository userRepository, PatientRepository patientRepository,
                                 MedicRepository medicRepository, TherapyRepository therapyRepository,
                                 ReportRepository reportRepository, UserService userService, PatientService patientService,
                                 MedicService medicService, TherapyService therapyService, NotificationRepository notificationRepository,
                                 NotificationService notificationService, ReportService reportService) {
    return args -> {
      // TODO: Remove in production
      log.info("Clearing database");
      notificationRepository.deleteAll();
      reportRepository.deleteAll();
      patientRepository.deleteAll();
      medicRepository.deleteAll();
      therapyRepository.deleteAll();
      userRepository.deleteAll();

      // Flush to ensure deletion is complete
      notificationRepository.flush();
      reportRepository.flush();
      patientRepository.flush();
      medicRepository.flush();
      therapyRepository.flush();
      userRepository.flush();

      // Preload Users
      User user1 = userService.create(new User("fabio@gmail.com", "fabio", Role.PATIENT));
      User user2 = userService.create(new User("paolo@gmail.com", "paolo", Role.ADMIN));
      User medic_user = userService.create(new User("medic@gmail.com", "medic", Role.MEDIC));
      log.info("Preloading user " + user1);
      log.info("Preloading user " + user2);
      log.info("Preloading user " + medic_user);

      // Preload Medics
      Medic medic = medicService.create(new Medic(medic_user, "sushila", "cacata"));
      log.info("Preloading medic " + medic);

      // Preload Therapies
      Therapy therapy1 = therapyService.create(new Therapy("Metformina", 2, 30.0, "Prendere durante i pasti"));
      log.info("Preloading therapy " + therapy1 + " for user " + user1);

      // Preload Patients
      Patient patient1 = patientService.create(new Patient(user1, "fabio", "fabibo", LocalDate.of(2001, 1, 1), medic));
      log.info("Preloading patient " + patient1);

      Report report = reportService.create(new Report(120, false,
              "cacca", "aiuto", LocalDateTime.of(LocalDate.of(2000,1,1),
              LocalTime.of(1,1,1)), "Insuline", 100, patient1));
      log.info("Preloading report " + report);

      Notification notif = notificationService.create(new Notification("Ok.", false, LocalDateTime.of(2025, 7,
              21, 20, 13), medic_user));

      log.info("Preloading notifications " + notif);

    };
  }

}
