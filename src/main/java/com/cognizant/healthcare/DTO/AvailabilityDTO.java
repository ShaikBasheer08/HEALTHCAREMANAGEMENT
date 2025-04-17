package com.cognizant.healthcare.DTO;
 
import com.cognizant.healthcare.constants.DayOfWeek;
import jakarta.validation.constraints.NotNull;
 
import lombok.Data;
 
@Data
public class AvailabilityDTO {
 
    private Long availabilityID;
 
   @NotNull(message = "Timeslot must not be null")
 
    private String timeslot; // e.g., "10:00 AM - 11:00 AM"
 
@NotNull(message = "Available day must not be null")
    private DayOfWeek availableDay; // Enum ensures valid day-of-week values
 
  //@NotNull(message = "Doctor details must not be null")
    private DoctorDTO doctor; // Associated doctor information
}
 
 