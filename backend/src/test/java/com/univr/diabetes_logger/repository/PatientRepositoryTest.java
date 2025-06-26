package com.univr.diabetes_logger.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import com.univr.diabetes_logger.model.Therapy;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.model.User.Role;

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
    Patient patient = new Patient(new User("testmail", "pass", Role.PATIENT), "TestFirstName", "TestLastName",
        LocalDate.of(2000, 1, 1), new Medic(new User("medicmail", "pass", Role.MEDIC), "TestMedic", "lastname"),
            new Therapy("Caccolina", 100, 420.69, "aiutatemi"));
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
