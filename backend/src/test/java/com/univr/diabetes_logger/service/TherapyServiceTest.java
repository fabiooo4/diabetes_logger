package com.univr.diabetes_logger.service;

import com.univr.diabetes_logger.model.*;
import com.univr.diabetes_logger.repository.TherapyRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * PatientServiceTest
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TherapyServiceTest {
    @Mock
    private TherapyRepository therapyRepository;

    @InjectMocks
    private TherapyService therapyService;

    private Therapy therapy;

    @BeforeEach
    public void setup() {
        therapy = new Therapy("Metformina", 3, 10.4, "None");
    }

    @Test
    @Order(1)
    public void createTherapyTest() {
        // precondition
        given(therapyRepository.save(therapy)).willReturn(therapy);

        // action
        Therapy savedTherapy = therapyService.create(therapy);

        // verify the output
        System.out.println(savedTherapy);
        assertThat(savedTherapy).isNotNull();
    }

    @Test
    @Order(2)
    public void getTherapyTest() {
        // precondition
        given(therapyRepository.findById(therapy.getId())).willReturn(Optional.of(therapy));

        // action
        Therapy existingTherapy = therapyService.getById(therapy.getId()).get();

        // verify
        System.out.println(existingTherapy);
        assertThat(existingTherapy).isNotNull();
    }

    @Test
    @Order(3)
    public void getAllTherapyTest() {
        // precondition
        given(therapyRepository.findAll()).willReturn(List.of(therapy));

        // action
        Iterable<Therapy> existingTherapies = therapyService.getAll();

        // verify
        System.out.println(existingTherapies);
        assertThat(existingTherapies).isNotNull();
    }

    @Test
    @Order(4)
    public void updatePatient() {
        // precondition
        given(therapyRepository.findById(therapy.getId())).willReturn(Optional.of(therapy));
        therapy.setDirections("Ciao");
        therapy.setDailyIntake(2);
        therapy.setAmount(10.0);
        therapy.setMedicine("No");
        given(therapyRepository.save(therapy)).willReturn(therapy);

        // action
        Therapy updated = therapyService.update(therapy.getId(), therapy);

        // verify
        System.out.println(updated);
        assertThat(updated.getDirections()).isEqualTo("Ciao");
        assertThat(updated.getDailyIntake()).isEqualTo(2);
        assertThat(updated.getAmount()).isEqualTo(10.0);
        assertThat(updated.getMedicine()).isEqualTo("No");
    }

    @Test
    @Order(5)
    public void deletePatient() {
        // precondition
        given(therapyRepository.findById(therapy.getId())).willReturn(Optional.of(therapy));

        // action
        Therapy deletedTherapy = therapyService.delete(therapy.getId());

        // verify
        System.out.println(deletedTherapy);
        assertThat(deletedTherapy).isEqualTo(therapy);
    }
}
