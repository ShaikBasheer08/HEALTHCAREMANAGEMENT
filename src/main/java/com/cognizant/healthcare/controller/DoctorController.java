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
    
        // Create a new doctor
        @PostMapping
        public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
            try {
                DoctorDTO createdDoctor = doctorService.createDoctor(doctorDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        // Get a doctor by ID
        @GetMapping("/{doctorID}")
        public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long doctorID) {
            Optional<DoctorDTO> doctorDTO = doctorService.getDoctorById(doctorID);
            return doctorDTO.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        }

        // Update doctor details
        @PutMapping("/{doctorID}")
        public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long doctorID,
                                                      @RequestBody DoctorDTO updatedDoctorDTO) {
            Optional<DoctorDTO> updatedDoctor = doctorService.updateDoctor(doctorID, updatedDoctorDTO);
            return updatedDoctor.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        }

        // Delete a doctor
        @DeleteMapping("/{doctorID}")
        public ResponseEntity<String> deleteDoctor(@PathVariable Long doctorID) {
            try {
                doctorService.deleteDoctor(doctorID);
                return ResponseEntity.ok("Doctor deleted successfully.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An error occurred while deleting the doctor.");
            }
        }

        // Create availability for a doctor
        @PostMapping("/{doctorID}/availability")
        public ResponseEntity<AvailabilityDTO> createDoctorAvailability(@PathVariable Long doctorID,
                                                                        @RequestBody AvailabilityDTO availabilityDTO) {
            try {
                AvailabilityDTO createdAvailability = doctorService.createDoctorAvailability(doctorID, availabilityDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdAvailability);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        // Get availability for a doctor
        @GetMapping("/{doctorID}/availability")
        public ResponseEntity<List<AvailabilityDTO>> getDoctorAvailability(@PathVariable Long doctorID) {
            try {
                List<AvailabilityDTO> availabilityList = doctorService.getDoctorAvailability(doctorID);
                return ResponseEntity.ok(availabilityList);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    


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
