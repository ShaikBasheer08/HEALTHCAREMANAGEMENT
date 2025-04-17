package com.cognizant.healthcare.DTO;
 
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 
import lombok.Data;
 
@Data
public class PatientDTO {
 
    private Long patientID;
 
    @NotNull(message = "User details must not be null")
    private UserDTO user; // Ensures that the associated user is provided
 
    @Min(value = 0, message = "Age must not be negative")
   @Max(value = 120, message = "Age must not exceed 120")
    private int age;
 
    @NotBlank(message = "Gender must not be blank")
    @Size(max = 10, message = "Gender must not exceed 10 characters")
    private String gender; // E.g., "Male", "Female", "Other"
 
  @Size(max = 500, message = "Current medications must not exceed 500 characters")
    private String currentMedications; // Optional field, but limited in length
 
    @Size(max = 1000, message = "Medical history must not exceed 1000 characters")
    private String medicalHistory; // Optional field, but limited in length
}
 
 