package com.cognizant.healthcare.entity;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import java.util.List;
 
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
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

	    private Long doctorID; // Use camel case for the field name

        @MapsId

	    @OneToOne(cascade = CascadeType.ALL)

	    @JoinColumn(name = "doctor_id")

        @JsonBackReference

        @JsonIgnore

	    private User user;
 
		private String qualification;

	    private String specialization;

	    private int experienceYears;

	    private String status;

	@JsonIgnore

	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Availability> availabilityList;


	@JsonIgnore

    @OneToMany(mappedBy = "doctor")

    private List<Appointment> doctorAppointments;

}

 