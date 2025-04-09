package com.cognizant.healthcare.controller;

import com.cognizant.healthcare.DTO.AvailabilityDTO;
import com.cognizant.healthcare.DTO.ConsultationDTO;
import com.cognizant.healthcare.DTO.DoctorDTO;
import com.cognizant.healthcare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // **Consultation Management**

    // Create a consultation based on appointment ID
    @PostMapping("/appointments/{appointmentId}/consultations")
    public ResponseEntity<ConsultationDTO> createConsultation(@PathVariable Long appointmentId,
                                                              @RequestBody ConsultationDTO consultationDTO) {
        try {
            ConsultationDTO createdConsultation = doctorService.createConsultation(appointmentId, consultationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdConsultation);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get consultations for a doctor
    @GetMapping("/{doctorID}/consultations")
    public ResponseEntity<List<ConsultationDTO>> getConsultationsByDoctor(@PathVariable Long doctorID) {
        try {
            List<ConsultationDTO> consultations = doctorService.getConsultationsByDoctor(doctorID);
            return ResponseEntity.ok(consultations);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get a specific consultation by ID
    @GetMapping("/consultations/{consultationID}")
    public ResponseEntity<ConsultationDTO> getConsultationById(@PathVariable Long consultationID) {
        try {
            ConsultationDTO consultation = doctorService.getConsultationById(consultationID);
            return ResponseEntity.ok(consultation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update a consultation
    @PutMapping("/consultations/{consultationID}")
    public ResponseEntity<ConsultationDTO> updateConsultation(@PathVariable Long consultationID,
                                                              @RequestBody ConsultationDTO consultationDTO) {
        try {
            ConsultationDTO updatedConsultation = doctorService.updateConsultation(consultationID, consultationDTO.getNotes());
            return ResponseEntity.ok(updatedConsultation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Delete a consultation
    @DeleteMapping("/consultations/{consultationID}")
    public ResponseEntity<String> deleteConsultation(@PathVariable Long consultationID) {
        try {
            doctorService.deleteConsultation(consultationID);
            return ResponseEntity.ok("Consultation deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consultation not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the consultation.");
        }
    }
}
