package com.univr.diabetes_logger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Rilevazione
 */
@Entity
@Table(name = "reports")
public class Report {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "glycemiaLevel")
    private int glycemiaLevel;
    @Column(name = "isBeforeMeal")
    private boolean beforeMeal;
    @Column(name = "symptoms")
    private String symptoms;
    @Column(name = "notes")
    private String notes;

    // Insulin Intake or any other prescription
    @Column(name = "day")
    private LocalDate day;
    @Column(name = "time")
    private LocalTime time;
    @Column(name = "medicine")
    private String medicine;
    @Column(name = "amount")
    private int amount; // mg/dL

    // I pazienti possiedono la terapia, basta fetcharla con l'ID tramite l'id dell' utente
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Patient patient;

    public Report(int glycemiaLevel, boolean beforeMeal, String symptoms, String notes,
                  LocalDate day, LocalTime time, String medicine, int amount, Patient patient) {
        this.glycemiaLevel = glycemiaLevel;
        this.beforeMeal = beforeMeal;

        // if beforeMeal ... > 130 ... sendNotif ...

        this.symptoms = symptoms;
        this.notes = notes;
        this.day = day;
        this.time = time;
        this.medicine = medicine;
        this.amount = amount;
        this.patient = patient;
    }

    public int getGlycemiaLevel() {
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

    public boolean getBeforeMeal() {
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

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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
                ", day=" + day +
                ", time=" + time +
                ", medicine='" + medicine + '\'' +
                ", amount=" + amount +
                ", patient=" + (patient != null ? patient.toString() : "null") +
                '}';
    }
}
