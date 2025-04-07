package com.cognizant.healthcare.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.cognizant.healthcare.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Custom query to find a patient by ID (if needed)
    Optional<Patient> findByPatientID(Long patientID);
}



