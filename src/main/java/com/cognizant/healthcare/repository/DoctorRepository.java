package com.cognizant.healthcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.healthcare.DTO.DoctorDTO;
import com.cognizant.healthcare.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	Optional<DoctorDTO> findByDoctorId(Long doctorID);

}
