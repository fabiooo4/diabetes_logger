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
      ReportRepository reportRepository, MedicChangeLogRepository medicChangeLogRepository, UserService userService,
      PatientService patientService,
      MedicService medicService, TherapyService therapyService, NotificationRepository notificationRepository,
      NotificationService notificationService, ReportService reportService,
      MedicChangeLogService medicChangeLogService) {
    return args -> {
      // Load an admin account if not exists
      if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
        userService.create(new User("admin@gmail.com", "admin", Role.ADMIN, true));
      }

      // log.info("Clearing database");
      //
      // medicChangeLogRepository.deleteAll();
      // medicChangeLogRepository.flush();
      // log.info("Cleared medic change logs");
      //
      // notificationRepository.deleteAll();
      // notificationRepository.flush();
      // log.info("Cleared notifications");
      //
      // reportRepository.deleteAll();
      // reportRepository.flush();
      // log.info("Cleared reports");
      //
      // patientRepository.deleteAll();
      // patientRepository.flush();
      // log.info("Cleared patients");
      //
      // medicRepository.deleteAll();
      // medicRepository.flush();
      // log.info("Cleared medics");
      //
      // userRepository.deleteAll();
      // userRepository.flush();
      // log.info("Cleared users");
      //
      // therapyRepository.deleteAll();
      // therapyRepository.flush();
      // log.info("Cleared therapies");
      //
      // // Preload Users
      // User user1 = userService.create(new User("patient@gmail.com", "patient",
      // Role.PATIENT, true));
      // User user2 = userService.create(new User("admin@gmail.com", "admin",
      // Role.ADMIN, true));
      // User medic_user = userService.create(new User("medic@gmail.com", "medic",
      // Role.MEDIC, true));
      // log.info("Preloading user " + user1);
      // log.info("Preloading user " + user2);
      // log.info("Preloading user " + medic_user);
      //
      // // Preload Medics
      // Medic medic = medicService.create(new Medic(medic_user, "MedicFirstName",
      // "MedicLastName"));
      // log.info("Preloading medic " + medic);
      //
      // // Preload Therapies
      // Therapy therapy1 = therapyService.create(new Therapy("Metformine", 2, 30.0,
      // "Take it only in the evening"));
      // log.info("Preloading therapy " + therapy1 + " for user " + user1);
      //
      // // Preload Patients
      // Patient patient1 = patientService.create(new Patient(user1,
      // "PatientFirstName", "PatientLastName", LocalDate.of(2001, 1, 1), medic));
      // log.info("Preloading patient " + patient1);
      //
      // Report report = reportService.create(new Report(120, false,
      // "Stomachache", "None", LocalDateTime.of(LocalDate.of(2000, 1, 1),
      // LocalTime.of(1, 1, 1)),
      // "Insuline", 100., patient1));
      // log.info("Preloading report " + report);
      //
      // Notification notif = notificationService.create(new Notification("Ok.",
      // false, LocalDateTime.of(2025, 7,
      // 21, 20, 13), medic_user));
      //
      // log.info("Preloading notifications " + notif);
    };
  }
}
