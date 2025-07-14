package com.univr.diabetes_logger.service;

import com.univr.diabetes_logger.model.*;
import com.univr.diabetes_logger.repository.NotificationRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.univr.diabetes_logger.model.User.Role;

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
public class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private Notification notif;

    @BeforeEach
    public void setup() {
        notif = new Notification("ciao", true,
                LocalDateTime.now(),
                new User("user@gmail.com", "ciao", Role.MEDIC, true));
    }

    @Test
    @Order(1)
    public void createNotificationTest() {
        // precondition
        given(notificationRepository.save(notif)).willReturn(notif);

        // action
        Notification savedNotif = notificationService.create(notif);

        // verify the output
        System.out.println(savedNotif);
        assertThat(savedNotif).isNotNull();
    }

    @Test
    @Order(2)
    public void getReportTest() {
        // precondition
        given(notificationRepository.findById(notif.getId())).willReturn(Optional.of(notif));

        // action
        Notification existingNotif = notificationService.getById(notif.getId()).get();

        // verify
        System.out.println(existingNotif);
        assertThat(existingNotif).isNotNull();
    }

    @Test
    @Order(3)
    public void getAllReportsTest() {
        // precondition
        given(notificationRepository.findAll()).willReturn(List.of(notif));

        // action
        Iterable<Notification> existingNotif = notificationService.getAll();

        // verify
        System.out.println(existingNotif);
        assertThat(existingNotif).isNotNull();
    }

    @Test
    @Order(4)
    public void updatePatient() {
        // precondition
        given(notificationRepository.findById(notif.getId())).willReturn(Optional.of(notif));
        notif.setSeen(true);
        notif.setCreatedAt(LocalDateTime.of(2018, 12, 1, 12, 0));
        notif.setMessage("prova");
        given(notificationRepository.save(notif)).willReturn(notif);

        // action
        Notification updated = notificationService.update(notif.getId(), notif);

        // verify
        System.out.println(updated);
        assertThat(updated.isSeen()).isEqualTo(true);
        assertThat(updated.getCreatedAt()).isEqualTo(LocalDateTime.of(2018,
                12, 1, 12, 0));
        assertThat(updated.getMessage()).isEqualTo("prova");
    }

    @Test
    @Order(5)
    public void deleteNotificationTest() {
        // precondition
        given(notificationRepository.findById(notif.getId())).willReturn(Optional.of(notif));

        // action
        Notification deletedNotif = notificationService.delete(notif.getId());

        // verify
        System.out.println(deletedNotif);
        assertThat(deletedNotif).isEqualTo(notif);
    }
}
