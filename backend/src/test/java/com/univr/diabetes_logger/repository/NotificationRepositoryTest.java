package com.univr.diabetes_logger.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.univr.diabetes_logger.model.Notification;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.model.User.Role;

/**
 * PatientRepositoryTest
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotificationRepositoryTest {
  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  @Order(1)
  @Rollback(value = false)
  public void createNotificationTest() {

    // Action
    User user = userRepository.save(new User("user@gmail.com", "ciao", Role.MEDIC, true));
    Notification notif = new Notification("ciao", true, LocalDateTime.now(), user);
    notificationRepository.save(notif);

    // Verify
    System.out.println(notif);
    assertThat(notif.getId()).isGreaterThan(0);
  }

  @Test
  @Order(2)
  public void getNotificationByIdTest() {
    // Action
    Notification found = notificationRepository.findById(1).get();

    // Verify
    System.out.println(found);
    assertThat(found.getId()).isEqualTo(1);
  }

  @Test
  @Order(3)
  public void getAllNotifsTest() {
    // Action
    List<Notification> notifs = notificationRepository.findAll();

    // Verify
    System.out.println(notifs);
    assertThat(notifs.size()).isGreaterThan(0);
  }

  @Test
  @Order(4)
  @Rollback(value = false)
  public void updateNotificationTest() {
    // Action
    Notification notif = notificationRepository.findById(1).get();
    notif.setSeen(true);
    notif.setCreatedAt(LocalDateTime.of(2018, 12, 1, 12, 0));
    notif.setMessage("prova");
    Notification updated = notificationRepository.save(notif);

    // Verify
    System.out.println(updated);
    assertThat(updated.isSeen()).isEqualTo(true);
    assertThat(updated.getCreatedAt()).isEqualTo(LocalDateTime.of(2018,
        12, 1, 12, 0));
    assertThat(updated.getMessage()).isEqualTo("prova");
  }

  @Test
  @Order(5)
  @Rollback(value = false)
  public void deleteNotificationTest() {
    // Action
    Notification patient = notificationRepository.findById(1).get();
    notificationRepository.delete(patient);

    // Verify
    Notification deleted = notificationRepository.findById(1).orElse(null);
    assertThat(deleted).isNull();
  }
}
