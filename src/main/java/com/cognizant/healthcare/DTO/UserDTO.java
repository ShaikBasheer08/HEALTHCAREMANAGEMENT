package com.cognizant.healthcare.DTO;
 
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
 
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
 
import lombok.Data;
 
@Data
public class UserDTO {
 
    private Long userID;
 
@NotBlank(message = "Name must not be blank")
@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
 
    @NotBlank(message = "Role must not be blank")
    @Pattern(
        regexp = "^(Doctor|Patient)$",
        message = "Role must be either 'Doctor' or 'Patient'"
    )
    private String role;
 
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    private String email;
 
    @NotBlank(message = "Phone number must not be blank")
    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Phone number must be a valid 10-digit number"
    )
    private String phone;
 
    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;
}
 