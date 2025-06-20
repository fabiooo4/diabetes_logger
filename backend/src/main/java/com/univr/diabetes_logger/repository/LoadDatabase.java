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

@Configuration
public class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(UserRepository userRepository, PatientRepository patientRepository,
      MedicRepository medicRepository) {
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
      User user1 = userRepository.save(new User("fabio@gmail.com", "fabio"));
      User user2 = userRepository.save(new User("paolo@gmail.com", "paolo"));
      User medic_user = userRepository.save(new User("medic@gmail.com", "medic", Role.MEDIC));
      log.info("Preloading user " + user1);
      log.info("Preloading user " + user2);
      log.info("Preloading user " + medic_user);

      // Preload Medics
      Medic medic = medicRepository.save(new Medic(medic_user, "sushila", "cacata"));
      log.info("Preloading medic " + medic);

      // Preload Patients
      Patient patient1 = patientRepository.save(new Patient(user1, "fabio", "fabibo", LocalDate.of(2001, 1, 1), medic));
      Patient patient2 = patientRepository
          .save(new Patient(user2, "paolo", "magidoof", LocalDate.of(2002, 2, 2), medic));

      log.info("Preloading patient " + patient1);
      log.info("Preloading patient " + patient2);
    };
  }

}
