package com.univr.diabetes_logger.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Terapia
 */
@Entity
@Table(name = "therapies")
public class Therapy {
  @Id // Primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  public Therapy() {
  }
}
