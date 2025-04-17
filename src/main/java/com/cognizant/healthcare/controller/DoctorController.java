package com.cognizant.healthcare.controller;
 
import com.cognizant.healthcare.DTO.AvailabilityDTO;
 
import com.cognizant.healthcare.DTO.ConsultationDTO;
import com.cognizant.healthcare.DTO.DoctorDTO;
//import com.cognizant.healthcare.exception.ResourceNotFoundException;
import com.cognizant.healthcare.service.DoctorService;
 
import jakarta.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
//import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController
@RequestMapping("/doctors")
public class DoctorController {
 
    @Autowired
    private DoctorService doctorService;
 
    // Create a new doctor (accessible by ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<DoctorDTO> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        try {
            DoctorDTO createdDoctor = doctorService.createDoctor(doctorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
// Get a doctor by ID
    @GetMapping("/{doctorID}")
    public ResponseEntity<DoctorDTO> getDoctorByID(@PathVariable Long doctorID) {
        Optional<DoctorDTO> doctorDTO = doctorService.getDoctorByID(doctorID);
        return doctorDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
 
 
    // Update doctor details (accessible by ADMIN)
    @PutMapping("/{doctorID}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long doctorID,
                                                   @Valid @RequestBody DoctorDTO updatedDoctorDTO) {
        Optional<DoctorDTO> updatedDoctor = doctorService.updateDoctor(doctorID, updatedDoctorDTO);
        return updatedDoctor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
 
    // Create availability for a doctor (accessible by DOCTOR)
    @PostMapping("/{doctorID}/availability")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<AvailabilityDTO> createDoctorAvailability(@PathVariable Long doctorID,
                                                                  @Valid @RequestBody AvailabilityDTO availabilityDTO) {
        try {
            AvailabilityDTO createdAvailability = doctorService.createDoctorAvailability(doctorID, availabilityDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAvailability);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
 
    // Create consultation (accessible by DOCTOR)
    @PostMapping("/appointments/{appointmentID}/consultations")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ConsultationDTO> createConsultation(@PathVariable Long appointmentID,
                                                               @Valid @RequestBody ConsultationDTO consultationDTO) {
        try {
            ConsultationDTO createdConsultation = doctorService.createConsultation(appointmentID, consultationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdConsultation);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
 
 
 
 
   
 