package com.cognizant.healthcare.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import jakarta.persistence.OneToMany;

@Data
@Entity
public class Doctor {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long doctorId; // Use camel case for the field name
        @MapsId
	    @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "doctor_id")
	    private User user;

		private String qualification;
	    private String specialization;
	    private int experienceYears;
	    private String status;

//
//    public Long getDoctorId() {
//			return doctorId;
//		}
//
//		public void setDoctorId(Long doctorId) {
//			this.doctorId = doctorId;
//		}
//
//		public User getUser() {
//			return user;
//		}
//
//		public void setUser(User user) {
//			this.user = user;
//		}
//
//		public String getQualification() {
//			return qualification;
//		}
//
//		public void setQualification(String qualification) {
//			this.qualification = qualification;
//		}
//
//		public String getSpecialization() {
//			return specialization;
//		}
//
//		public void setSpecialization(String specialization) {
//			this.specialization = specialization;
//		}
//
//		public int getExperienceYears() {
//			return experienceYears;
//		}
//
//		public void setExperienceYears(int experienceYears) {
//			this.experienceYears = experienceYears;
//		}
//
//		public String getStatus() {
//			return status;
//		}
//
//		public void setStatus(String status) {
//			this.status = status;
//		}
//
//		public List<Availability> getAvailabilityList() {
//			return availabilityList;
//		}
//
//		public void setAvailabilityList(List<Availability> availabilityList) {
//			this.availabilityList = availabilityList;
//		}
//
//		public List<Appointment> getDoctorAppointments() {
//			return doctorAppointments;
//		}
//
//		public void setDoctorAppointments(List<Appointment> doctorAppointments) {
//			this.doctorAppointments = doctorAppointments;
//		}

    @OneToMany(mappedBy = "doctor")
    private List<Availability> availabilityList;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> doctorAppointments;
}
