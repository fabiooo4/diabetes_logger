package com.univr.diabetes_logger.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.Patient;

/**
 * PatientRepositoryTest
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientRepositoryTest {
  @Autowired
  private PatientRepository patientRepository;

  @Test
  @DisplayName("Test 1:Save Patient Test")
  @Order(1)
  @Rollback(value = false)
  public void savePatientTest() {

    // Action
    Patient patient = new Patient("TestFirstName", "TestLastName", 11, new Medic("TestMedic"));
    patientRepository.save(patient);

    // Verify
    System.out.println(patient);
    Assertions.assertThat(patient.getId()).isGreaterThan(0);
  }
}
