package com.cognizant.healthcare.DTO;
 
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 
 
import lombok.Data;
 
@Data
public class DoctorDTO {
 
    private Long doctorID;
 
  @NotNull(message = "User details must not be null")
    private UserDTO user; // Ensures associated user information is provided
 
  @NotBlank(message = "Qualification must not be blank")
   @Size(max = 100, message = "Qualification must not exceed 100 characters")
    private String qualification;
 
    @NotBlank(message = "Specialization must not be blank")
    @Size(max = 50, message = "Specialization must not exceed 50 characters")
    private String specialization;
 
    @Min(value = 0, message = "Experience years must not be negative")
    @Max(value = 60, message = "Experience years cannot exceed 60")
    private int experienceYears;
 
   @NotBlank(message = "Status must not be blank")
    @Size(max = 20, message = "Status must not exceed 20 characters")
    private String status; // e.g., "Active" or "Inactive"
}
 
 