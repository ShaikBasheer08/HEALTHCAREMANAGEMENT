package com.cognizant.healthcare.DTO;

import java.time.LocalDateTime;

public class ConsultationDTO {
    private Long consultationID;
    private AppointmentDTO appointment;
    private LocalDateTime consultationDate;
    private String notes; // Consultation notes
    private String prescription; // Prescribed medications
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
	public LocalDateTime getConsultationDate() {
		return consultationDate;
	}
	public void setConsultationDate(LocalDateTime consultationDate) {
		this.consultationDate = consultationDate;
	}

    // Getters and setters
}

