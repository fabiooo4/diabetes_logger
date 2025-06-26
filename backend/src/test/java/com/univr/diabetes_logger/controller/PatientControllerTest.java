package com.univr.diabetes_logger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.Therapy;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.model.User.Role;
import com.univr.diabetes_logger.service.PatientService;

import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PatientControllerTest
 */
@WebMvcTest(PatientController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private PatientService patientService;

  @Autowired
  private ObjectMapper objectMapper;

  private Patient patient;

  @BeforeEach
  public void setup() {
    patient = new Patient(new User("usermail", "pass1", Role.PATIENT), "John", "Cena", LocalDate.of(2000, 1, 1),
        new Medic(new User("medicmail", "medicpass", Role.MEDIC), "CenaMedic", "LastName"),
            new Therapy("Caccolina", 100, 420.69, "aiutatemi"));
    patient.setId(1); // Set a mock ID for testing
  }

  // Post Controller
  @Test
  @Order(1)
  public void createPatientTest() throws Exception {
    // precondition
    given(patientService.create(any(Patient.class))).willReturn(patient);

    // action
    ResultActions response = mockMvc.perform(post("/patients")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(patient)));

    // verify
    response.andDo(print()).andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstName",
            is(patient.getFirstName())))
        .andExpect(jsonPath("$.lastName",
            is(patient.getLastName())))
        .andExpect(jsonPath("$.birthDate",
            is(patient.getBirthDate())))
        .andExpect(jsonPath("$.referralMedic.id", is(patient.getReferralMedic().getId())));
  }

  // Get Controller
  @Test
  @Order(2)
  public void getAllPatientsTest() throws Exception {
    // precondition
    List<Patient> patientsList = new ArrayList<>();
    patientsList.add(patient);
    patientsList
        .add(
                new Patient(new User("testmail", "testpass", Role.PATIENT), "test", "test", LocalDate.of(2000, 1, 1),
                new Medic(new User("testmedicmail", "testpass", Role.MEDIC), "testMedic", "lastname"),
                new Therapy("Caccolina", 100, 420.69, "aiutatemi")));
    given(patientService.getAll()).willReturn(patientsList);

    // action
    ResultActions response = mockMvc.perform(get("/patients"));

    // verify
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()",
            is(patientsList.size())));

  }

  @Test
  @Order(3)
  public void getPatientByIdTest() throws Exception {
    // precondition
    given(patientService.getById(patient.getId())).willReturn(Optional.of(patient));

    // action
    ResultActions response = mockMvc.perform(get("/patients/{id}", patient.getId()));

    // verify
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName",
            is(patient.getFirstName())))
        .andExpect(jsonPath("$.lastName",
            is(patient.getLastName())))
        .andExpect(jsonPath("$.birthDate",
            is(patient.getBirthDate())))
        .andExpect(jsonPath("$.referralMedic.id", is(patient.getReferralMedic().getId())));
  }

  // Put Controller
  @Test
  @Order(4)
  public void updatePatientTest() throws Exception {
    // precondition
    patient.setFirstName("UpdatedFirst");
    patient.setLastName("UpdatedLast");
    given(patientService.update(any(Integer.class), any(Patient.class))).willReturn(patient);

    // action
    ResultActions response = mockMvc.perform(put("/patients/{id}", patient.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(patient)));

    // verify
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName",
            is(patient.getFirstName())))
        .andExpect(jsonPath("$.lastName",
            is(patient.getLastName())))
        .andExpect(jsonPath("$.birthDate",
            is(patient.getBirthDate())))
        .andExpect(jsonPath("$.referralMedic.id", is(patient.getReferralMedic().getId())));
  }

  // Delete Controller
  @Test
  @Order(5)
  public void deletePatientTest() throws Exception {
    // precondition
    given(patientService.delete(patient.getId())).willReturn(patient);

    // action
    ResultActions response = mockMvc.perform(delete("/patients/{id}", patient.getId()));

    // verify
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName",
            is(patient.getFirstName())))
        .andExpect(jsonPath("$.lastName",
            is(patient.getLastName())))
        .andExpect(jsonPath("$.birthDate",
            is(patient.getBirthDate())))
        .andExpect(jsonPath("$.referralMedic.id", is(patient.getReferralMedic().getId())));
  }

}
