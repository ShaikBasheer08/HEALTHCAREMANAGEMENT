package com.cognizant.healthcare.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.healthcare.DTO.DoctorDTO;
import com.cognizant.healthcare.entity.Doctor;
import com.cognizant.healthcare.entity.User;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	Optional<DoctorDTO> findByDoctorID(Long doctorID);
Doctor findByUser(User user);
}
