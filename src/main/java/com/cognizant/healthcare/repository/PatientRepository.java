package com.cognizant.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.healthcare.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	


}
