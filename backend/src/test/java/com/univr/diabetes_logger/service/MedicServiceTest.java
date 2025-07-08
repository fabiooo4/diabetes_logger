package com.univr.diabetes_logger.service;

import com.univr.diabetes_logger.repository.MedicRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.model.User.Role;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * PatientServiceTest
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicServiceTest {
    @Mock
    private MedicRepository medicRepository;

    @InjectMocks
    private MedicService medicService;

    private Medic medic;

    @BeforeEach
    public void setup() {
        medic = new Medic(new User("medic@gmail.com", "medic", Role.MEDIC, true),
                "name", "lastname");
    }

    @Test
    @Order(1)
    public void createMedicTest() {
        // precondition
        given(medicRepository.save(medic)).willReturn(medic);

        // action
        Medic savedMedic = medicService.create(medic);

        // verify the output
        System.out.println(savedMedic);
        assertThat(savedMedic).isNotNull();
    }

    @Test
    @Order(2)
    public void getMedicById() {
        // precondition
        given(medicRepository.findById(medic.getId())).willReturn(Optional.of(medic));

        // action
        Medic existingMedic = medicService.getById(medic.getId()).get();

        // verify
        System.out.println(existingMedic);
        assertThat(existingMedic).isNotNull();
    }

    @Test
    @Order(3)
    public void getAllMedics() {
        // precondition
        given(medicRepository.findAll()).willReturn(List.of(medic));

        // action
        Iterable<Medic> existingMedics = medicService.getAll();

        // verify
        System.out.println(existingMedics);
        assertThat(existingMedics).isNotNull();
    }

    @Test
    @Order(4)
    public void updatePatient() {
        // precondition
        given(medicRepository.findById(medic.getId())).willReturn(Optional.of(medic));
        medic.setFirstName("UpdatedFirst");
        medic.setLastName("UpdatedLast");
        given(medicRepository.save(medic)).willReturn(medic);

        // action
        Medic updatedPatient = medicService.update(medic.getId(), medic);

        // verify
        System.out.println(updatedPatient);
        assertThat(updatedPatient.getFirstName()).isEqualTo("UpdatedFirst");
        assertThat(updatedPatient.getLastName()).isEqualTo("UpdatedLast");
    }

    @Test
    @Order(5)
    public void deletePatient() {
        // precondition
        given(medicRepository.findById(medic.getId())).willReturn(Optional.of(medic));

        // action
        Medic deletedMedic = medicService.delete(medic.getId());

        // verify
        System.out.println(deletedMedic);
        assertThat(deletedMedic).isEqualTo(medic);
    }
}
