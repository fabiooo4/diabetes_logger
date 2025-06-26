package com.univr.diabetes_logger.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Patient
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "patients")
public class Patient {
  @Id // Primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "firstName")
  private String firstName;
  @Column(name = "lastName")
  private String lastName;
  @Column(name = "birthDate")
  private LocalDate birthDate;

  @ManyToOne
  @JoinColumn(name = "medic_id", referencedColumnName = "id", nullable = true)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Medic referralMedic;

  @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private User user;

  @ManyToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "therapy_id", referencedColumnName = "id", nullable = true)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Therapy therapy;

  protected Patient() {
  }

  public Patient(User user, String firstName, String lastName, LocalDate birthDate, Medic referralMedic, Therapy therapy) {
    this.user = user;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.referralMedic = referralMedic;
    this.therapy = therapy;
  }

  public Integer getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String name) {
    this.firstName = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String surname) {
    this.lastName = surname;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public Medic getReferralMedic() {
    return referralMedic;
  }

  public void setReferralMedic(Medic medic) {
    this.referralMedic = medic;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate
        + ", referralMedic=" + referralMedic + ", therapy=" + therapy + ", user=" + user + "]";
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Therapy getTherapy() {
    return therapy;
  }

  public void setTherapy(Therapy therapy) {
    this.therapy = therapy;
  }

}
