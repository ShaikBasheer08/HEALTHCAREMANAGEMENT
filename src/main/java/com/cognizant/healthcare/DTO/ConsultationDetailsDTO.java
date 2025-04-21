package com.cognizant.healthcare.DTO;
 
import java.time.LocalDateTime;
 
public class ConsultationDetailsDTO {
    private Long consultationID;
    private LocalDateTime consultationDate;
    private String notes;
    private String prescription;
 
    // Getters and setters
    public Long getConsultationID() {
        return consultationID;
    }
 
    public void setConsultationID(Long consultationID) {
        this.consultationID = consultationID;
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
 