package com.cognizant.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cognizant.healthcare.entity.Appointment;

import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findByPatient_PatientID(Long patientID);

	List<Appointment> findByDoctor_DoctorID(Long doctorID);



    
}