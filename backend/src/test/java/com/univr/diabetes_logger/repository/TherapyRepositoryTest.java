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

/**
 * PatientRepositoryTest
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TherapyRepositoryTest {
    @Autowired
    private TherapyRepository therapyRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void createTherapyTest() {

        // Action
        Therapy therapy = new Therapy("Metformina", 3, 10.4, "Nessuna");
        therapyRepository.save(therapy);

        // Verify
        System.out.println(therapy);
        assertThat(therapy.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getTherapyByIdTest() {
        // Action
        Therapy found = therapyRepository.findById(1).get();

        // Verify
        System.out.println(found);
        assertThat(found.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void getAllPatientsTest() {
        // Action
        List<Therapy> patients = therapyRepository.findAll();

        // Verify
        System.out.println(patients);
        assertThat(patients.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateTherapyTest() {
        // Action
        Therapy therapy = therapyRepository.findById(1).get();
        therapy.setDirections("Ciao");
        therapy.setDailyIntake(2);
        therapy.setAmount(10.0);
        therapy.setMedicine("No");
        Therapy updated = therapyRepository.save(therapy);

        // Verify
        System.out.println(updated);

        assertThat(updated.getDirections()).isEqualTo("Ciao");
        assertThat(updated.getDailyIntake()).isEqualTo(2);
        assertThat(updated.getAmount()).isEqualTo(10.0);
        assertThat(updated.getMedicine()).isEqualTo("No");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deletePatientTest() {
        // Action
        Therapy therapy = therapyRepository.findById(1).get();
        therapyRepository.delete(therapy);

        // Verify
        Therapy deleted = therapyRepository.findById(1).orElse(null);
        assertThat(deleted).isNull();
    }
}
