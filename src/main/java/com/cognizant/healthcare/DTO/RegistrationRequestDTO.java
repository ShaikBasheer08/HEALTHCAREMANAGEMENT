package com.cognizant.healthcare.DTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
 
import lombok.Data;
 
@Data
public class RegistrationRequestDTO {
 
  @NotBlank(message = "Name must not be blank")
   @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
 
    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;
 
    @NotBlank(message = "Role must not be blank")
    @Pattern(
        regexp = "^(ROLE_DOCTOR|ROLE_PATIENT)$",
        message = "Role must be either 'ROLE_DOCTOR' or 'ROLE_PATIENT'"
    )
    private String role;
 
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    private String email;
 
    @NotBlank(message = "Phone must not be blank")
    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Phone number must be a valid 10-digit number"
    )
    private String phone;
 
     
    @Size(max = 100, message = "Qualification must not exceed 100 characters")
    private String qualification;
 
    @Size(max = 50, message = "Specialization must not exceed 50 characters")
    private String specialization;
 
    //@NotNull(message = "Experience years must not be null")
    @Min(value = 0, message = "Experience years must be a positive number")
    @Max(value = 100, message = "Experience years must be less than or equal to 100")
    private Integer experienceYears;
    
    
    @Size(max = 20, message = "Status must not exceed 20 characters")
    private String status;
 
    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 120, message = "Age must be at most 120")
    private Integer age;
 
   // @NotBlank(message = "Gender must not be blank")
    @Size(max = 10, message = "Gender must not exceed 10 characters")
    private String gender;
 
   @Size(max = 500, message = "Current medications must not exceed 500 characters")
    private String currentMedications;
 
    @Size(max = 1000, message = "Medical history must not exceed 1000 characters")
    private String medicalHistory;
}
 
 
 
 