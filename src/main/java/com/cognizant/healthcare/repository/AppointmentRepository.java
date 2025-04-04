package com.cognizant.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.healthcare.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
