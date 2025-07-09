package com.univr.diabetes_logger.model;

import java.util.LinkedHashSet;
import java.util.Set;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Medic
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

  @OneToMany(mappedBy = "referralMedic")
  private Set<Patient> patients = new LinkedHashSet<Patient>();

  @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private User user;

  protected Medic() {
  }

  public Medic(User user, String firstName, String lastName) {
    this.user = user;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public void updateMedic(Medic medic) {

    String firstName = medic.getFirstName();
    if (firstName != null) {
      setFirstName(firstName);
    }

    String lastName = medic.getLastName();
    if (lastName != null) {
      setLastName(lastName);
    }

  }

  public String getEmail() {
    return this.getUser().getEmail();
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
    return "Medic [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", user=" + user + "]";
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
