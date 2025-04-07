package com.cognizant.healthcare.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private Long userID;
    private String name;
    private String role; // "Doctor" or "Patient"
    private String email;
    private String phone;
    private String Password;

    // Getters and setters
}
