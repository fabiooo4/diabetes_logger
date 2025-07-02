package com.univr.diabetes_logger.repository;

import com.univr.diabetes_logger.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Optional<Notification> findById(Integer id);
}
