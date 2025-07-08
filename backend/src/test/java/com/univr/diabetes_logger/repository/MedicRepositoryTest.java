package com.univr.diabetes_logger.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
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
public class MedicRepositoryTest {
    @Autowired
    private MedicRepository medicRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void createMedicTest() {

        // Action
        Medic medic = new Medic(new User("medic@gmail.com", "medic", Role.MEDIC, true),
        "name", "lastname");
        medicRepository.save(medic);

        // Verify
        System.out.println(medic);
        assertThat(medic.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getMedicByIdTest() {
        // Action
        Medic found = medicRepository.findById(1).get();

        // Verify
        System.out.println(found);
        assertThat(found.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void getAllMedicsTest() {
        // Action
        List<Medic> medics = medicRepository.findAll();

        // Verify
        System.out.println(medics);
        assertThat(medics.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateMedicTest() {
        // Action
        Medic med = medicRepository.findById(1).get();
        med.setFirstName("UpdatedFirstName");
        med.setLastName("UpdatedLastName");
        Medic updated = medicRepository.save(med);

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
        Medic patient = medicRepository.findById(1).get();
        medicRepository.delete(patient);

        // Verify
        Medic deleted = medicRepository.findById(1).orElse(null);
        assertThat(deleted).isNull();
    }
}
