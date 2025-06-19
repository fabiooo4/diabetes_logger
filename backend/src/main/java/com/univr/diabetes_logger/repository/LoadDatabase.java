package com.univr.diabetes_logger.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.Patient;

@Configuration
public class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(PatientRepository repository) {
    return args -> {
      log.info("Clearing database");
      repository.deleteAll();

      log.info(
          "Preloading " + repository.save(new Patient("Fabio", "Fabio", 99, "fabio@gmail.com", new Medic("Medic1"))));
      log.info(
          "Preloading " + repository.save(new Patient("Paolo", "Paolo", 66, "paolo@gmail.com", new Medic("Medic2"))));
    };
  }

}
