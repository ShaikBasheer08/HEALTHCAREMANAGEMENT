package com.cognizant.healthcare.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultationID;

    @OneToOne
    @JoinColumn(name = "appointmentID", nullable = false) // Links to Appointment table
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "doctorID", nullable = false) // Links to Doctor table
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patientID", nullable = false) // Links to Patient table
    private Patient patient;

    private LocalDateTime consultationDate;
    private String notes;
    private String prescription;

    // Getters and Setters
    public Long getConsultationID() {
        return consultationID;
    }

    public void setConsultationID(Long consultationID) {
        this.consultationID = consultationID;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(LocalDateTime consultationDate) {
        this.consultationDate = consultationDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
}
