package com.cognizant.healthcare.DTO;
 
import java.time.LocalDate;
import java.time.LocalTime;
 
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class AppointmentResponseDTO {
    private Long appointmentID;
    private LocalDate date;
    @NotNull(message = "Timeslot must not be null")
    private String timeslot;
    private String status;
    private PatientInfoDTO patient; // Simplified patient info
    private DoctorInfoDTO doctor;   // Simplified doctor info
 
    // Getters and setters
 
    public Long getAppointmentID() {
        return appointmentID;
    }
 
    public void setAppointmentID(Long appointmentID) {
        this.appointmentID = appointmentID;
    }
 
    public LocalDate getDate() {
        return date;
    }
 
    public void setDate(LocalDate date) {
        this.date = date;
    }
 
    public String getTimeslot() {
        return timeslot;
    }
 
    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public PatientInfoDTO getPatient() {
        return patient;
    }
 
    public void setPatient(PatientInfoDTO patient) {
        this.patient = patient;
    }
 
    public DoctorInfoDTO getDoctor() {
        return doctor;
    }
 
    public void setDoctor(DoctorInfoDTO doctor) {
        this.doctor = doctor;
    }
}
 