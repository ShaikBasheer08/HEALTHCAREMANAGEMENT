package com.cognizant.healthcare.DTO;
 
public class DoctorInfoDTO {
    private Long doctorID;
    private String name; // Just the name from the User
    private String qualification;
    private String specialization;
 
    // Getters and setters
 
    public Long getDoctorID() {
        return doctorID;
    }
 
    public void setDoctorID(Long doctorID) {
        this.doctorID = doctorID;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getQualification() {
        return qualification;
    }
 
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
 
    public String getSpecialization() {
        return specialization;
    }
 
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
 
 