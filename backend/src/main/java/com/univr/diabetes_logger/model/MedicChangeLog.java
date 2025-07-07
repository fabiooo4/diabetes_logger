package com.univr.diabetes_logger.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


/**
 * MedicChangeLog
 */
@Entity
@Table(name = "medicChangeLog")
public class MedicChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "medic_id", referencedColumnName = "id", nullable = false)
    private Medic medic; // who made the change

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    private Patient patient; // to whom the change was made

    @Column(name = "action")
    private String action; // e.g. "Added therapy", "Removed therapy", etc.

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    protected MedicChangeLog() {}

    public MedicChangeLog(Medic medic, Patient patient, String action, LocalDateTime timestamp) {
        this.medic = medic;
        this.patient = patient;
        this.action = action;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Medic getMedic() {
        return medic;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
