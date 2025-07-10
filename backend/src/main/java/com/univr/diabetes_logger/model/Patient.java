package com.univr.diabetes_logger.model;

import java.time.LocalDate;
import java.util.Objects;

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

  @Column(name = "riskFactor", nullable = true)
  private String riskFactor;
  @Column(name = "previousPatologies", nullable = true)
  private String previousPatologies;
  @Column(name = "medicNotes", nullable = true)
  private String medicNotes;

  @ManyToOne
  @JoinColumn(name = "medic_id", referencedColumnName = "id", nullable = true)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Medic referralMedic;

  @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private User user;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "therapy_id", referencedColumnName = "id", nullable = true)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Therapy therapy;

  protected Patient() {
  }

  public Patient(User user, String firstName, String lastName,
      LocalDate birthDate, Medic referralMedic) {
    this.user = user;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.referralMedic = referralMedic;
  }

  public void updatePatient(Patient patient) throws IllegalArgumentException {
    String firstName = patient.getFirstName();
    if (firstName != null) {
      setFirstName(firstName);
    }

    String lastName = patient.getLastName();
    if (lastName != null) {
      setLastName(lastName);
    }

    LocalDate birthDate = patient.getBirthDate();
    if (birthDate != null) {
      setBirthDate(birthDate);
    }

    // The following are nullable
    Therapy therapy = patient.getTherapy();
    if (therapy != null) {
      if (therapy.getAmount() == null || therapy.getMedicine() == null
          || therapy.getDailyIntake() == null || therapy.getDirections() == null) {
        throw new IllegalArgumentException("Therapy fields cannot be null");
      }

      patient.getTherapy().setId(null);
      setTherapy(patient.getTherapy());
    } else {
      setTherapy(null);
    }

    setReferralMedic(patient.getReferralMedic());
    setPreviousPatologies(patient.getPreviousPatologies());
    setRiskFactor(patient.getRiskFactor());
    setMedicNotes(patient.getMedicNotes());
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
        + ", riskFactor=" + riskFactor + ", previousPatologies=" + previousPatologies + ", medicNotes=" + medicNotes
        + ", referralMedic=" + referralMedic + ", user=" + user + ", therapy=" + therapy + "]";
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

  public String getRiskFactor() {
    return riskFactor;
  }

  public void setRiskFactor(String riskFactor) {
    this.riskFactor = riskFactor;
  }

  public String getPreviousPatologies() {
    return previousPatologies;
  }

  public void setPreviousPatologies(String previousPatologies) {
    this.previousPatologies = previousPatologies;
  }

  public String getMedicNotes() {
    return medicNotes;
  }

  public void setMedicNotes(String medicNotes) {
    this.medicNotes = medicNotes;
  }

  public String actionPerformed(Patient patient) {
    String ret = "Modified: ";
    String notModified = ret;

    if ((patient.getReferralMedic() == null && this.getReferralMedic() != null)
        || (this.getReferralMedic() == null && patient.getReferralMedic() != null)
        || (patient.getReferralMedic() != null && this.getReferralMedic() != null
            && !Objects.equals(patient.getReferralMedic().getId(), this.getReferralMedic().getId()))) {
      ret += "referral medic, ";
    }

    if (!Objects.equals(patient.getTherapy(), this.getTherapy())) {
      ret += "therapy, ";
    }

    if (!Objects.equals(patient.getRiskFactor(), this.getRiskFactor())) {
      ret += "risk factor, ";
    }

    if (!Objects.equals(patient.getPreviousPatologies(), this.getPreviousPatologies())) {
      ret += "previous patologies, ";
    }

    if (!Objects.equals(patient.getMedicNotes(), this.getMedicNotes())) {
      ret += "medic notes, ";
    }

    if (ret.equals(notModified)) {
      return null;
    }

    return ret.substring(0, ret.length() - 2);
  }
}
