package com.cognizant.healthcare.DTO;
 
import java.time.LocalDateTime;
 
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 
public class ConsultationDTO {
 
    private Long consultationID;
 
    @NotNull(message = "Appointment details must not be null")
    private AppointmentDTO appointment;
 
    @NotNull(message = "Consultation date must not be null")
    @FutureOrPresent(message = "Consultation date must be in the present or future")
    private LocalDateTime consultationDate;
 
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes; // Consultation notes
 
   @Size(max = 300, message = "Prescription must not exceed 300 characters")
    private String prescription; // Prescribed medications
 
    // Getters and setters
    public Long getConsultationID() {
        return consultationID;
    }
 
    public void setConsultationID(Long consultationID) {
        this.consultationID = consultationID;
    }
 
    public AppointmentDTO getAppointment() {
        return appointment;
    }
 
    public void setAppointment(AppointmentDTO appointment) {
        this.appointment = appointment;
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
 
 
