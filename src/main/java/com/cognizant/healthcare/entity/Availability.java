package com.cognizant.healthcare.entity;

import com.cognizant.healthcare.constants.DayOfWeek;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long availabilityID;

    private String timeslot;
    public Long getAvailabilityID() {
		return availabilityID;
	}

	public void setAvailabilityID(Long availabilityID) {
		this.availabilityID = availabilityID;
	}

	public String getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(String timeslot) {
		this.timeslot = timeslot;
	}

	public DayOfWeek getAvailableDay() {
		return availableDay;
	}

	public void setAvailableDay(DayOfWeek availableDay) {
		this.availableDay = availableDay;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@Enumerated(EnumType.STRING) // Use EnumType.STRING to store enum values as strings in the database
    private DayOfWeek availableDay; 

    @ManyToOne
    @JoinColumn(name = "doctorID")
    private Doctor doctor;
}
