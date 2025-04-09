package com.cognizant.healthcare.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AppointmentDTO {
    private Long appointmentID;
    private PatientDTO patient;
    private DoctorDTO doctor;
    private LocalDate date;
    private String timeslot; // e.g., "10:00 AM - 11:00 AM"
    private String status; // e.g., "Booked", "Cancelled", "Completed"

    // Getters and setters
}
