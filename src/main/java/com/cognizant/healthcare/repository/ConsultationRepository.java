package com.cognizant.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.healthcare.entity.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

}
