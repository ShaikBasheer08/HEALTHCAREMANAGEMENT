package com.cognizant.healthcare.DTO;

import lombok.Data;

@Data
public class PatientDTO {
    private Long patientID;
    private UserDTO user;
    private int age;
    private String gender;
    private String currentMedications;
    private String medicalHistory;
    
}
