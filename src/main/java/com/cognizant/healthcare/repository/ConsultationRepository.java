package com.cognizant.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.healthcare.entity.Consultation;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    // Custom query to find consultations by Patient ID
    List<Consultation> findByAppointment_Patient_PatientID(Long patientId);

    // Custom query to find consultations by Appointment ID
    List<Consultation> findByAppointment_AppointmentID(Long appointmentId);
}
