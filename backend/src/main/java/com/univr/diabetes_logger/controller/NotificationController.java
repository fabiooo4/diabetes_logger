package com.univr.diabetes_logger.controller;

import com.univr.diabetes_logger.model.Notification;
import com.univr.diabetes_logger.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * NotificationController
 */
@RestController
@RequestMapping(path = "/notifications")
public class NotificationController {

  private final NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping
  public Iterable<Notification> getAllNotifications() {
    return notificationService.getAll();
  }

  @GetMapping("/{id}")
  public Optional<Notification> getNotificationById(@PathVariable Integer id) {
    return notificationService.getById(id);
  }

  @GetMapping("/user/{userId}/{id}")
  public Optional<Notification> getNotificationByUser(@PathVariable Integer userId, @PathVariable Integer id) {
    List<Notification> listOfReports = notificationService.getAllByUserId(userId);

    return listOfReports.stream().filter(notif -> id.equals(notif.getId())).findFirst();
  }

  @GetMapping("/user/{userId}")
  public Iterable<Notification> getAllNotificationByUser(@PathVariable Integer userId) {
    return notificationService.getAllByUserId(userId);
  }

  @PostMapping("/user/{userId}")
  public ResponseEntity<Notification> createUserNotification(@RequestBody Notification notification,
      @PathVariable Integer userId,
      UriComponentsBuilder uriBuilder) {
    Notification created = notificationService.createOnUser(notification, userId).orElseThrow();

    var uri = uriBuilder.path("/notifications/user/{userId}/{id}").buildAndExpand(userId, created.getId()).toUri();
    return ResponseEntity.created(uri).body(created);
  }

  @PatchMapping("/user/{userId}/{id}")
  public Notification toggleSeenUserNotifications(@PathVariable Integer userId, @PathVariable Integer id) {
    return notificationService.toggleSeen(id);
  }

  @PatchMapping("/user/{userId}")
  public Iterable<Notification> toggleSeenAllUserNotifications(@PathVariable Integer userId) {

    List<Notification> notifications = notificationService.getAllByUserId(userId);

    for (Notification notif : notifications) {
      notificationService.setSeen(notif.getId());
    }

    return notifications;
  }

  @PutMapping("/user/{userId}/{id}")
  public Notification updateUserNotification(@RequestBody Notification notification, @PathVariable Integer id) {
    return notificationService.updateOnUser(id, notification);
  }

  @PutMapping("/{id}")
  public Notification updateNotification(@RequestBody Notification notification, @PathVariable Integer id) {
    return notificationService.update(id, notification);
  }

  @DeleteMapping("/{id}")
  public Notification deleteNotificationById(@PathVariable Integer id) {
    return notificationService.delete(id);
  }

  @DeleteMapping("/user/{userId}/{id}")
  public Notification deleteUserNotificationByUserId(@PathVariable Integer id) {
    return notificationService.delete(id);
  }
}
