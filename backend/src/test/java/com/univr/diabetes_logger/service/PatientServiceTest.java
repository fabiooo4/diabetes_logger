package com.univr.diabetes_logger.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.repository.PatientRepository;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * PatientServiceTest
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientServiceTest {
  @Mock
  private PatientRepository patientRepository;

  @InjectMocks
  private PatientServiceImpl patientService;

  private Patient patient;

  @BeforeEach
  public void setup() {
    patient = new Patient("John", "Cena", 40, new Medic("CenaMedic"));
  }

  @Test
  @Order(1)
  public void createPatientTest() {
    // precondition
    given(patientRepository.save(patient)).willReturn(patient);

    // action
    Patient savedPatient = patientService.createPatient(patient);

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
    Patient existingPatient = patientService.getPatientById(patient.getId()).get();

    // verify
    System.out.println(existingPatient);
    assertThat(existingPatient).isNotNull();
  }
}
