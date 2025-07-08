package com.univr.diabetes_logger.service;

import com.univr.diabetes_logger.model.MedicChangeLog;
import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.repository.MedicChangeLogRepository;
import com.univr.diabetes_logger.repository.MedicRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.model.User.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * PatientServiceTest
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicChangeLogServiceTest {
    @Mock
    private MedicChangeLogRepository mclRepository;

    @InjectMocks
    private MedicChangeLogService mclService;

    private MedicChangeLog mcl;

    @BeforeEach
    public void setup() {
        Medic medic = new Medic(new User("medic@gmail.com", "medic", Role.MEDIC, true),
                "name", "lastname");
        Patient patient = new Patient(new User("testmail", "pass", User.Role.PATIENT,true),
                "TestFirstName", "TestLastName",
                LocalDate.of(2000, 1, 1), new Medic(new User("medicmail",
                "pass", User.Role.MEDIC, true), "TestMedic", "lastname"));
        mcl = new MedicChangeLog(medic, patient, "Modified x",
                LocalDateTime.of(2018, 12, 1, 12, 0));
    }

    @Test
    @Order(1)
    public void createMclTest() {
        // precondition
        given(mclRepository.save(mcl)).willReturn(mcl);

        // action
        MedicChangeLog savedMedic = mclService.create(mcl);

        // verify the output
        System.out.println(savedMedic);
        assertThat(savedMedic).isNotNull();
    }

    @Test
    @Order(2)
    public void getMclTest() {
        // precondition
        given(mclRepository.findById(mcl.getId())).willReturn(Optional.of(mcl));

        // action
        MedicChangeLog existingMedic = mclService.getById(mcl.getId()).get();

        // verify
        System.out.println(existingMedic);
        assertThat(existingMedic).isNotNull();
    }

    @Test
    @Order(3)
    public void getAllMcls() {
        // precondition
        given(mclRepository.findAll()).willReturn(List.of(mcl));

        // action
        Iterable<MedicChangeLog> existingMedics = mclService.getAll();

        // verify
        System.out.println(existingMedics);
        assertThat(existingMedics).isNotNull();
    }

    @Test
    @Order(4)
    public void updateMclTest() {
        // precondition
        given(mclRepository.findById(mcl.getId())).willReturn(Optional.of(mcl));
        mcl.setAction("Update");
        mcl.setTimestamp(LocalDateTime.of(2018, 12, 1, 12, 0));
        given(mclRepository.save(mcl)).willReturn(mcl);

        // action
        MedicChangeLog updated = mclService.update(mcl.getId(), mcl);

        // verify
        System.out.println(updated);
        assertThat(updated.getAction()).isEqualTo("Update");
        assertThat(updated.getTimestamp()).isEqualTo(LocalDateTime.of(2018, 12, 1, 12, 0));
    }

    @Test
    @Order(5)
    public void deleteMclTest() {
        // precondition
        given(mclRepository.findById(mcl.getId())).willReturn(Optional.of(mcl));

        // action
        MedicChangeLog deletedMcl = mclService.delete(mcl.getId());

        // verify
        System.out.println(deletedMcl);
        assertThat(deletedMcl).isEqualTo(mcl);
    }
}
