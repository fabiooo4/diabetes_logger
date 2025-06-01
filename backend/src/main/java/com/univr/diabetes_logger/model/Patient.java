package com.univr.diabetes_logger.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
  @Column(name = "age")
  private int age;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "medic_id", referencedColumnName = "id")
  private Medic referralMedic;

  protected Patient() {
  }

  public Patient(String firstName, String lastName, int age, Medic referralMedic) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.referralMedic = referralMedic;
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

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Medic getReferralMedic() {
    return referralMedic;
  }

  public void setReferralMedic(Medic medic) {
    this.referralMedic = medic;
  }

}
