package com.univr.diabetes_logger.model;

import jakarta.persistence.*;

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

  @Column(name = "medicine")
  private String medicine;
  @Column(name = "dailyIntake")
  private Integer dailyIntake;
  @Column(name = "amount")
  private Double amount;
  @Column(name = "directions")
  private String directions;

  protected Therapy() {}

  public Therapy(String medicine, Integer dailyIntake, Double amount, String directions) {
    this.medicine = medicine;
    this.dailyIntake = dailyIntake;
    this.amount = amount;
    this.directions = directions;
  }

  public void updateTherapy(Therapy therapy) {

    String medicine = therapy.getMedicine();
    if(medicine != null) {
      this.medicine = medicine;
    }

    Double amount = therapy.getAmount();
    if(amount != null) {
      this.amount = amount;
    }

    String directions = therapy.getDirections();
    if(directions != null) {
      this.directions = directions;
    }

    Integer dailyIntake = therapy.getDailyIntake();
    if(dailyIntake != null) {
      this.dailyIntake = dailyIntake;
    }
  }

  public Integer getId() {
      return id;
  }

  public String getMedicine() {
      return medicine;
  }

  public Integer getDailyIntake() {
      return dailyIntake;
  }

  public Double getAmount() {
    return amount;
  }

  public String getDirections() {
    return directions;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setMedicine(String medicine) {
    this.medicine = medicine;
  }

  public void setDailyIntake(Integer dailyIntake) {
    this.dailyIntake = dailyIntake;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public void setDirections(String directions) {
    this.directions = directions;
  }

  public String toString() {
    return "Therapy: [id=" + id + ", medicine=" + medicine + ", dailyIntake=" +
            dailyIntake + ", amount=" + amount + ", directions=" + directions + "]";
  }
}
