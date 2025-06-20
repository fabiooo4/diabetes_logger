package com.univr.diabetes_logger.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

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
  @Order(1)
  @Rollback(value = false)
  public void createPatientTest() {

    // Action
    Patient patient = new Patient("TestFirstName", "TestLastName", 11, "testemail@gmail.com", new Medic("TestMedic", "lastname", "email"));
    patientRepository.save(patient);

    // Verify
    System.out.println(patient);
    assertThat(patient.getId()).isGreaterThan(0);
  }

  @Test
  @Order(2)
  public void getPatientByIdTest() {
    // Action
    Patient found = patientRepository.findById(1).get();

    // Verify
    System.out.println(found);
    assertThat(found.getId()).isEqualTo(1);
  }

  @Test
  @Order(3)
  public void getAllPatientsTest() {
    // Action
    List<Patient> patients = patientRepository.findAll();

    // Verify
    System.out.println(patients);
    assertThat(patients.size()).isGreaterThan(0);
  }

  @Test
  @Order(4)
  @Rollback(value = false)
  public void updatePatientTest() {
    // Action
    Patient patient = patientRepository.findById(1).get();
    patient.setFirstName("UpdatedFirstName");
    patient.setLastName("UpdatedLastName");
    Patient updated = patientRepository.save(patient);

    // Verify
    System.out.println(updated);
    assertThat(updated.getFirstName()).isEqualTo("UpdatedFirstName");
    assertThat(updated.getLastName()).isEqualTo("UpdatedLastName");
  }

  @Test
  @Order(5)
  @Rollback(value = false)
  public void deletePatientTest() {
    // Action
    Patient patient = patientRepository.findById(1).get();
    patientRepository.delete(patient);

    // Verify
    Patient deleted = patientRepository.findById(1).orElse(null);
    assertThat(deleted).isNull();
  }
}
