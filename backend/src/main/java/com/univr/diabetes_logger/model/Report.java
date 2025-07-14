package com.univr.diabetes_logger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

/**
 * Report
 */
@Entity
@Table(name = "reports")
public class Report {

  @Id // Primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "glycemiaLevel")
  private Integer glycemiaLevel;
  @Column(name = "dateTime")
  private LocalDateTime dateTime;
  @Column(name = "isBeforeMeal")
  private Boolean beforeMeal;
  @Column(name = "symptoms")
  private String symptoms; // Extra
  @Column(name = "notes")
  private String notes; // Extra

  // Insulin Intake or any other prescription
  @Column(name = "medicine")
  private String medicine;
  @Column(name = "amount")
  private Double amount; // mg/dL

  @ManyToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = true)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Patient patient;

  protected Report() {

  }

  public Report(Integer glycemiaLevel, boolean beforeMeal, String symptoms, String notes,
      LocalDateTime dateTime, String medicine, Double amount, Patient patient) {
    this.glycemiaLevel = glycemiaLevel;
    this.beforeMeal = beforeMeal;
    this.patient = patient;
    this.symptoms = symptoms;
    this.notes = notes;
    this.dateTime = dateTime;
    this.medicine = medicine;
    this.amount = amount;

  }

  public Integer getGlycemiaLevel() {
    return glycemiaLevel;
  }

  public void setGlycemiaLevel(int glycemiaLevel) {
    this.glycemiaLevel = glycemiaLevel;
  }

  public boolean isBeforeMeal() {
    return beforeMeal;
  }

  public void setBeforeMeal(boolean beforeMeal) {
    this.beforeMeal = beforeMeal;
  }

  public Boolean getBeforeMeal() {
    return beforeMeal;
  }

  public String getSymptoms() {
    return symptoms;
  }

  public void setSymptoms(String symptoms) {
    this.symptoms = symptoms;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getMedicine() {
    return medicine;
  }

  public void setMedicine(String medicine) {
    this.medicine = medicine;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  @Override
  public String toString() {
    return "Report{" +
        "glycemiaLevel=" + glycemiaLevel +
        ", beforeMeal=" + (beforeMeal ? "before meal" : "after meal") +
        ", symptoms='" + symptoms + '\'' +
        ", notes='" + notes + '\'' +
        ", dateTime=" + dateTime +
        ", medicine='" + medicine + '\'' +
        ", amount=" + amount +
        ", patient=" + (patient != null ? patient.toString() : "null") +
        '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
