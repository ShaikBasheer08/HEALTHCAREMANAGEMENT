package com.cognizant.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.healthcare.DTO.DoctorDTO;
import com.cognizant.healthcare.DTO.PatientDTO;
import com.cognizant.healthcare.entity.Consultation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {



	 Optional<Consultation> findByAppointment_AppointmentID(Long appointmentID);


	List<Consultation> findByDoctor_DoctorID(Long doctorID);
	List<Consultation> findByAppointment_Patient_PatientID(Long patientID);



}
