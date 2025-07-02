package com.univr.diabetes_logger.service;

import com.univr.diabetes_logger.model.Therapy;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.model.User.Role;
import com.univr.diabetes_logger.repository.PatientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * PatientServiceTest

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientServiceTest {
  @Mock
  private PatientRepository patientRepository;

  @InjectMocks
  private PatientService patientService;

  private Patient patient;

  @BeforeEach
  public void setup() {
    patient = new Patient(new User("testmail", "testpass", Role.PATIENT), "test", "test", LocalDate.of(2000, 1, 1),
        new Medic(new User("medicmail", "medicpass", Role.MEDIC), "testMedic", "lastname"),
    new Therapy("Caccolina", 100, 420.69, "aiutatemi"));
  }

  @Test
  @Order(1)
  public void createPatientTest() {
    // precondition
    given(patientRepository.save(patient)).willReturn(patient);

    // action
    Patient savedPatient = patientService.create(patient);

    // verify the output
    System.out.println(savedPatient);
    assertThat(savedPatient).isNotNull();
  }

  @Test
  @Order(2)
  public void getPatientById() {
    // precondition
    given(patientRepository.findById(patient.getId())).willReturn(Optional.of(patient));

    // action
    Patient existingPatient = patientService.getById(patient.getId()).get();

    // verify
    System.out.println(existingPatient);
    assertThat(existingPatient).isNotNull();
  }

  @Test
  @Order(3)
  public void getAllPatients() {
    // precondition
    given(patientRepository.findAll()).willReturn(List.of(patient));

    // action
    Iterable<Patient> existingPatients = patientService.getAll();

    // verify
    System.out.println(existingPatients);
    assertThat(existingPatients).isNotNull();
  }

  @Test
  @Order(4)
  public void updatePatient() {
    // precondition
    given(patientRepository.findById(patient.getId())).willReturn(Optional.of(patient));
    patient.setFirstName("UpdatedFirst");
    patient.setLastName("UpdatedLast");
    given(patientRepository.save(patient)).willReturn(patient);

    // action
    Patient updatedPatient = patientService.update(patient.getId(), patient);

    // verify
    System.out.println(updatedPatient);
    assertThat(updatedPatient.getFirstName()).isEqualTo("UpdatedFirst");
    assertThat(updatedPatient.getLastName()).isEqualTo("UpdatedLast");
  }

  @Test
  @Order(5)
  public void deletePatient() {
    // precondition
    given(patientRepository.findById(patient.getId())).willReturn(Optional.of(patient));

    // action
    Patient deletedPatient = patientService.delete(patient.getId());

    // verify
    System.out.println(deletedPatient);
    assertThat(deletedPatient).isEqualTo(patient);
  }
}
*/