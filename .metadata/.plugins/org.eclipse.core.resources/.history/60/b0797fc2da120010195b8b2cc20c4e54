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
import jakarta.persistence.OneToMany;

import lombok.Data;
@Data

@Entity
public class Doctor {
	@Id
	private Long doctorId;
    @OneToOne (cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name ="doctorId")
    private User user;

    private String qualification;
    private String specialization;
    private int experienceYears;
    private String status;

    @OneToMany(mappedBy = "doctor")
    private List<Availability> availabilityList;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> doctorAppointments;
}
