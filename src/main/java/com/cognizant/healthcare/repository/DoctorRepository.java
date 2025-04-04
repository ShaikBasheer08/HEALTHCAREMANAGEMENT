package com.cognizant.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.healthcare.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
