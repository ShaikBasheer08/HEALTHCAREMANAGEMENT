package com.cognizant.healthcare.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
@Data

@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String name;
    private String role;
    private String email;
    private String phone;
    private String password;

    @OneToOne(mappedBy = "user")
    @JsonManagedReference// Bi-directional mapping
    private Doctor doctor;

    @JsonManagedReference
    @OneToOne(mappedBy = "user") // Bi-directional mapping
    private Patient patient;
}
