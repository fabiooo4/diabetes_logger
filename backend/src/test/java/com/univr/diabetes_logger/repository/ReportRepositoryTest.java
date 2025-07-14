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

/**
 * PatientRepositoryTest
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReportRepositoryTest {
    @Autowired
    private ReportRepository reportRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void createReportTest() {

        // Action
        Patient patient = new Patient(new User("testmail", "pass", User.Role.PATIENT,true),
                "TestFirstName", "TestLastName",
                LocalDate.of(2000, 1, 1), new Medic(new User("medicmail",
                "pass", User.Role.MEDIC, true), "TestMedic", "lastname"));
        Report report = new Report(120, true, "None", "None",
                LocalDateTime.now(), "Insuline", 3., patient);
        reportRepository.save(report);

        // Verify
        System.out.println(report);
        assertThat(report.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getReportByIdTest() {
        // Action
        Report found = reportRepository.findById(1).get();

        // Verify
        System.out.println(found);
        assertThat(found.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void getAllReportsTest() {
        // Action
        List<Report> patients = reportRepository.findAll();

        // Verify
        System.out.println(patients);
        assertThat(patients.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateTherapyTest() {
        // Action
        Report report = reportRepository.findById(1).get();
        report.setGlycemiaLevel(100);
        report.setBeforeMeal(false);
        report.setDateTime(LocalDateTime.of(2000, 1,1,10,10,10));
        report.setSymptoms("Ciao");
        report.setNotes("Ciao");
        report.setAmount(3.);
        report.setMedicine("No");
        Report updated = reportRepository.save(report);

        // Verify
        System.out.println(updated);

        assertThat(updated.getGlycemiaLevel()).isEqualTo(100);
        assertThat(updated.getBeforeMeal()).isFalse();
        assertThat(updated.getDateTime()).isEqualTo(LocalDateTime.of(2000, 1,1,10,10,10));
        assertThat(updated.getSymptoms()).isEqualTo("Ciao");
        assertThat(updated.getNotes()).isEqualTo("Ciao");
        assertThat(updated.getAmount()).isEqualTo(3);
        assertThat(updated.getMedicine()).isEqualTo("No");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteReportTest() {
        // Action
        Report therapy = reportRepository.findById(1).get();
        reportRepository.delete(therapy);

        // Verify
        Report deleted = reportRepository.findById(1).orElse(null);
        assertThat(deleted).isNull();
    }
}
