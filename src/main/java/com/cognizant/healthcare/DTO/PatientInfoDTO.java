package com.cognizant.healthcare.DTO;

public class PatientInfoDTO {
    private Long patientID;
    private String name; // Just the name from the User
    private Integer age;
    private String gender;
 
    // Getters and setters
 
    public Long getPatientID() {
        return patientID;
    }
 
    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public Integer getAge() {
        return age;
    }
 
    public void setAge(Integer age) {
        this.age = age;
    }
 
    public String getGender() {
        return gender;
    }
 
    public void setGender(String gender) {
        this.gender = gender;
    }
}
 