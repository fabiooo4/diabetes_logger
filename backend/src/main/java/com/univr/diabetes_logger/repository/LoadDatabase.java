package com.univr.diabetes_logger.repository;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.model.User.Role;
import com.univr.diabetes_logger.service.MedicService;
import com.univr.diabetes_logger.service.PatientService;
import com.univr.diabetes_logger.service.UserService;

@Configuration
public class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(UserRepository userRepository, PatientRepository patientRepository,
      MedicRepository medicRepository, UserService userService, PatientService patientService,
      MedicService medicService) {
    return args -> {
      // TODO: Remove in production
      log.info("Clearing database");
      patientRepository.deleteAll();
      medicRepository.deleteAll();
      userRepository.deleteAll();

      // Flush to ensure deletion is complete
      patientRepository.flush();
      medicRepository.flush();
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

      // Preload Patients
      Patient patient1 = patientService.create(new Patient(user1, "fabio", "fabibo", LocalDate.of(2001, 1, 1), medic));

      log.info("Preloading patient " + patient1);
    };
  }

}
