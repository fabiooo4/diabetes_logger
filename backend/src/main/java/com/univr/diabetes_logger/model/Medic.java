package com.univr.diabetes_logger.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Medico
 */
@Entity
@Table(name = "medics")
public class Medic {
  @Id // Primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "firstName")
  private String firstName;
  @Column(name = "lastName")
  private String lastName;
  @Column(name = "email")
  private String email;

  protected Medic() {
  }

  public Medic(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
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

  @Override
  public String toString() {
    return "Medic [id=" + id + ", name=" + firstName + "]";
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
