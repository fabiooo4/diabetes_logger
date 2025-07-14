package com.univr.diabetes_logger.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.univr.diabetes_logger.model.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.univr.diabetes_logger.model.User.Role;

/**
 * PatientRepositoryTest
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicChangeLogRepositoryTest {
  @Autowired
  private MedicChangeLogRepository medicChangeLogRepository;

  @Autowired
  private MedicRepository medicRepository;

  @Autowired
  private PatientRepository patientRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  @Order(1)
  @Rollback(value = false)
  public void createMedicChangeLogTest() {

    // Action
    User medicUser = userRepository.save(new User("medic@gmail.com", "medic", Role.MEDIC, true));

    Medic medic = new Medic(medicUser,
        "name", "lastname");
    medic = medicRepository.save(medic);

    User patientUser = userRepository.save(new User("testmail", "pass", User.Role.PATIENT, true));
    Patient patient = new Patient(patientUser, "TestFirstName", "TestLastName",
        LocalDate.of(2000, 1, 1), medic);
    patient = patientRepository.save(patient);

    MedicChangeLog medicChangeLog = new MedicChangeLog(medic, patient, "Modified x",
        LocalDateTime.of(2018, 12, 1, 12, 0));

    medicChangeLogRepository.save(medicChangeLog);

    // Verify
    System.out.println(medicChangeLog);
    assertThat(medicChangeLog.getId()).isNotNull();
  }

  @Test
  @Order(2)
  public void getLogByIdTest() {
    // Action
    MedicChangeLog found = medicChangeLogRepository.findById(1).get();

    // Verify
    System.out.println(found);
    assertThat(found.getId()).isEqualTo(1);
  }

  @Test
  @Order(3)
  public void getAllMedicChangeLogTest() {
    // Action
    List<MedicChangeLog> mcl = medicChangeLogRepository.findAll();

    // Verify
    System.out.println(mcl);
    assertThat(mcl.size()).isGreaterThan(0);
  }

  @Test
  @Order(4)
  @Rollback(value = false)
  public void updateMedicChangeLogTest() {
    // Action
    MedicChangeLog mcl = medicChangeLogRepository.findById(1).get();
    mcl.setAction("Update");
    mcl.setTimestamp(LocalDateTime.of(2018, 12, 1, 12, 0));
    MedicChangeLog updated = medicChangeLogRepository.save(mcl);

    // Verify
    System.out.println(updated);
    assertThat(updated.getAction()).isEqualTo("Update");
    assertThat(updated.getTimestamp()).isEqualTo(LocalDateTime.of(2018, 12, 1, 12, 0));
  }

  @Test
  @Order(5)
  @Rollback(value = false)
  public void deleteChangeLogTest() {
    // Action
    MedicChangeLog patient = medicChangeLogRepository.findById(1).get();
    medicChangeLogRepository.delete(patient);

    // Verify
    MedicChangeLog deleted = medicChangeLogRepository.findById(1).orElse(null);
    assertThat(deleted).isNull();
  }
}
