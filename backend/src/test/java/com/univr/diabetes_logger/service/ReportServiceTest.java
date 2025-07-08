package com.univr.diabetes_logger.service;

import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.Report;
import com.univr.diabetes_logger.repository.MedicRepository;
import com.univr.diabetes_logger.repository.ReportRepository;
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
public class ReportServiceTest {
    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    private Report report;

    @BeforeEach
    public void setup() {
        Patient patient = new Patient(new User("testmail", "pass", User.Role.PATIENT,true),
                "TestFirstName", "TestLastName",
                LocalDate.of(2000, 1, 1), new Medic(new User("medicmail",
                "pass", User.Role.MEDIC, true), "TestMedic", "lastname"));
        report = new Report(120, true, "None", "None",
                LocalDateTime.now(), "Insuline", 3, patient);
    }

    @Test
    @Order(1)
    public void createReportTest() {
        // precondition
        given(reportRepository.save(report)).willReturn(report);

        // action
        Report savedReport = reportService.create(report);

        // verify the output
        System.out.println(savedReport);
        assertThat(savedReport).isNotNull();
    }

    @Test
    @Order(2)
    public void getReportTest() {
        // precondition
        given(reportRepository.findById(report.getId())).willReturn(Optional.of(report));

        // action
        Report existingReport = reportService.getById(report.getId()).get();

        // verify
        System.out.println(existingReport);
        assertThat(existingReport).isNotNull();
    }

    @Test
    @Order(3)
    public void getAllReportsTest() {
        // precondition
        given(reportRepository.findAll()).willReturn(List.of(report));

        // action
        Iterable<Report> existingReports = reportService.getAll();

        // verify
        System.out.println(existingReports);
        assertThat(existingReports).isNotNull();
    }

    @Test
    @Order(4)
    public void updatePatient() {
        // precondition
        given(reportRepository.findById(report.getId())).willReturn(Optional.of(report));
        report.setGlycemiaLevel(100);
        report.setBeforeMeal(false);
        report.setDateTime(LocalDateTime.of(2000, 1,1,10,10,10));
        report.setSymptoms("Ciao");
        report.setNotes("Ciao");
        report.setAmount(3);
        report.setMedicine("No");
        given(reportRepository.save(report)).willReturn(report);

        // action
        Report updated = reportService.update(report.getId(), report);

        // verify
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
    public void deletePatient() {
        // precondition
        given(reportRepository.findById(report.getId())).willReturn(Optional.of(report));

        // action
        Report deletedReport = reportService.delete(report.getId());

        // verify
        System.out.println(deletedReport);
        assertThat(deletedReport).isEqualTo(report);
    }
}
