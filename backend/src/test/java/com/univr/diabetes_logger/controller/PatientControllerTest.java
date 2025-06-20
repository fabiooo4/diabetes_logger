package com.univr.diabetes_logger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.service.PatientService;

import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
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
    patient = new Patient("John", "Cena", 40, "johncena@gmail.com", new Medic("CenaMedic", "LastName", "email"));
    patient.setId(1); // Set a mock ID for testing
  }

  // Post Controller
  @Test
  @Order(1)
  public void createPatientTest() throws Exception {
    // precondition
    given(patientService.createPatient(any(Patient.class))).willReturn(patient);

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
        .andExpect(jsonPath("$.age",
            is(patient.getAge())))
        .andExpect(jsonPath("$.email",
            is(patient.getEmail())))
        .andExpect(jsonPath("$.referralMedic.id", is(patient.getReferralMedic().getId())))
        .andExpect(jsonPath("$.referralMedic.name", is(patient.getReferralMedic().getFirstName())));
  }

  // Get Controller
  @Test
  @Order(2)
  public void getAllPatientsTest() throws Exception {
    // precondition
    List<Patient> patientsList = new ArrayList<>();
    patientsList.add(patient);
    patientsList
        .add(new Patient("Kanye", "West", 33, "kanyewest@gmail.com", new Medic("KanyeMedic", "lastname", "email")));
    given(patientService.getAllPatients()).willReturn(patientsList);

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
    given(patientService.getPatientById(patient.getId())).willReturn(Optional.of(patient));

    // action
    ResultActions response = mockMvc.perform(get("/patients/{id}", patient.getId()));

    // verify
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName",
            is(patient.getFirstName())))
        .andExpect(jsonPath("$.lastName",
            is(patient.getLastName())))
        .andExpect(jsonPath("$.age",
            is(patient.getAge())))
        .andExpect(jsonPath("$.email",
            is(patient.getEmail())))
        .andExpect(jsonPath("$.referralMedic.id", is(patient.getReferralMedic().getId())))
        .andExpect(jsonPath("$.referralMedic.name", is(patient.getReferralMedic().getFirstName())));
  }

  // Put Controller
  @Test
  @Order(4)
  public void updatePatientTest() throws Exception {
    // precondition
    patient.setFirstName("UpdatedFirst");
    patient.setLastName("UpdatedLast");
    given(patientService.updatePatient(any(Integer.class), any(Patient.class))).willReturn(patient);

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
        .andExpect(jsonPath("$.age",
            is(patient.getAge())))
        .andExpect(jsonPath("$.email",
            is(patient.getEmail())))
        .andExpect(jsonPath("$.referralMedic.id", is(patient.getReferralMedic().getId())))
        .andExpect(jsonPath("$.referralMedic.name", is(patient.getReferralMedic().getFirstName())));
  }

  // Delete Controller
  @Test
  @Order(5)
  public void deletePatientTest() throws Exception {
    // precondition
    given(patientService.deletePatient(patient.getId())).willReturn(patient);

    // action
    ResultActions response = mockMvc.perform(delete("/patients/{id}", patient.getId()));

    // verify
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName",
            is(patient.getFirstName())))
        .andExpect(jsonPath("$.lastName",
            is(patient.getLastName())))
        .andExpect(jsonPath("$.age",
            is(patient.getAge())))
        .andExpect(jsonPath("$.email",
            is(patient.getEmail())))
        .andExpect(jsonPath("$.referralMedic.id", is(patient.getReferralMedic().getId())))
        .andExpect(jsonPath("$.referralMedic.name", is(patient.getReferralMedic().getFirstName())));
  }

}
