package com.cognizant.healthcare.entity;
 
import java.util.List;
 
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
 
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
 
@Entity
@Data
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long patientID;
 
	@MapsId
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "patient_id")
	@JsonBackReference
	@JsonIgnore
	private User user;
 
	public Long getPatientID() {
		return patientID;
	}
 
	public void setPatientID(Long patientID) {
		this.patientID = patientID;
	}
 
	private int age;
	private String gender;
	private String currentMedications;
	private String medicalHistory;
	@JsonIgnore
	@OneToMany(mappedBy = "patient")
	private List<Appointment> patientAppointments;
 

	public List<Appointment> getPatientAppointments() {
		return patientAppointments;
	}
 
	public void setPatientAppointments(List<Appointment> patientAppointments) {
		this.patientAppointments = patientAppointments;
	}
 
	
}
 
 