package com.univr.diabetes_logger.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Integer id;

  @Column(name = "message")
  private String message;

  @Column(name = "seen")
  private boolean seen;

  @Column(name = "createdAt")
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private User user;

  protected Notification() {
  }

  public Notification(String message, boolean seen, LocalDateTime createdAt) {
    this.message = message;
    this.seen = seen;
    this.createdAt = createdAt;
  }

  public Notification(String message, boolean seen, LocalDateTime createdAt, User user) {
    this.message = message;
    this.seen = seen;
    this.createdAt = createdAt;
    this.user = user;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isSeen() {
    return seen;
  }

  public void setSeen(boolean seen) {
    this.seen = seen;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void toggleSeen() {
    this.setSeen(!this.isSeen());
  }
}
