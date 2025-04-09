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

import lombok.Data;
@Data

@Entity
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long availabilityID;

    private String timeslot;
    @Enumerated(EnumType.STRING) // Use EnumType.STRING to store enum values as strings in the database
    private DayOfWeek availableDay; 

    @ManyToOne
    @JoinColumn(name = "doctorID")
    private Doctor doctor;
}
