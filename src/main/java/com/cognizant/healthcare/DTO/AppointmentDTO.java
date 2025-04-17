package com.cognizant.healthcare.DTO;
 
 
import java.time.LocalDate;
 
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
 
import jakarta.validation.constraints.Size;
import lombok.Data;
 
@Data
public class AppointmentDTO {
    private Long appointmentID;
 
    @NotNull(message = "Patient details must not be null")
    private PatientDTO patient;
 
   @NotNull(message = "Doctor details must not be null")
    private DoctorDTO doctor;
 
  @NotNull(message = "Date must not be null")
  @Future(message = "Appointment date must be in the future")
    private LocalDate date;
 
    @NotNull(message = "Timeslot must not be null")
    private String timeslot;
 
  @NotNull(message = "Status must not be null")
    @Size(min = 1, max = 20, message = "Status must be between 1 and 20 characters long")
    private String status;
}
 
 