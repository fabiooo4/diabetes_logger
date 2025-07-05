package com.univr.diabetes_logger.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * User
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
public class User {

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public enum Role {
    PATIENT,
    MEDIC,
    ADMIN
  };

  @Id // Primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "email")
  private String email;
  @Column(name = "password")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  @Column(name = "role")
  private String role;
  @Column(name = "verified")
  private boolean verified;

  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Patient patient;

  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Medic medic;

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Set<Notification> notifications;

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public Medic getMedic() {
    return medic;
  }

  public void setMedic(Medic medic) {
    this.medic = medic;
  }

  protected User() {
  }

  public User(String email, String password, Role role, boolean verified) {
    this.email = email;
    this.password = password;
      this.verified = verified;

      if (role == null) {
      this.role = null;
    } else {
      this.role = role.toString();
    }
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    if (role == null) {
      return null;
    } else {
      return Role.valueOf(role);
    }
  }

  public void setRole(Role role) {
    if (role != null) {
      this.role = role.toString();
    }
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", email=" + email + ", password=" + password + ", role=" + role + "]";
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}
