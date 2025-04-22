package com.cognizant.healthcare.controller;

import com.cognizant.healthcare.DTO.AppointmentDTO;
import com.cognizant.healthcare.DTO.AvailabilityDTO;
import com.cognizant.healthcare.DTO.ConsultationDTO;
import com.cognizant.healthcare.DTO.DoctorDTO;
import com.cognizant.healthcare.exception.ResourceNotFoundException;
import com.cognizant.healthcare.exception.DuplicateRecordException;
import com.cognizant.healthcare.service.DoctorService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // ✅ Create Doctor
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<DoctorDTO> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO createdDoctor = doctorService.createDoctor(doctorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
        }

    // ✅ Get Doctor by ID
    @GetMapping("/{doctorID}")
    public ResponseEntity<DoctorDTO> getDoctorByID(@PathVariable Long doctorID) {
        Optional<DoctorDTO> doctorDTO = doctorService.getDoctorByID(doctorID);
        return doctorDTO.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + doctorID + " not found"));
    }

    // ✅ Update Doctor
    @PutMapping("/{doctorID}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long doctorID,
                                                  @Valid @RequestBody DoctorDTO updatedDoctorDTO) {
        Optional<DoctorDTO> updatedDoctor = doctorService.updateDoctor(doctorID, updatedDoctorDTO);
        return updatedDoctor.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + doctorID + " not found"));
    }

    // ✅ Delete Doctor
    @DeleteMapping("/{doctorID}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long doctorID) {
        doctorService.deleteDoctor(doctorID);
        return ResponseEntity.noContent().build();
    }

    // ✅ Create Availability for Doctor
    @PostMapping("/{doctorID}/availability")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<AvailabilityDTO> createDoctorAvailability(@PathVariable Long doctorID,
                                                                    @Valid @RequestBody AvailabilityDTO availabilityDTO) {
        AvailabilityDTO createdAvailability = doctorService.createDoctorAvailability(doctorID, availabilityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAvailability);
    }

    // ✅ Get Availability for Doctor
    @GetMapping("/{doctorID}/availability")
    public ResponseEntity<List<AvailabilityDTO>> getDoctorAvailability(@PathVariable Long doctorID) {
        List<AvailabilityDTO> availabilityList = doctorService.getDoctorAvailability(doctorID);
        return ResponseEntity.ok(availabilityList);
    }

    // ✅ Get Appointments by Doctor
    @GetMapping("/{doctorID}/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctor(@PathVariable Long doctorID) {
        List<AppointmentDTO> appointmentList = doctorService.getAppointmentsByDoctorId(doctorID);
        return ResponseEntity.ok(appointmentList);
    }

    // ✅ Create Consultation
    @PostMapping("/appointments/{appointmentID}/consultations")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ConsultationDTO> createConsultation(@PathVariable Long appointmentID,
                                                              @Valid @RequestBody ConsultationDTO consultationDTO) {
        try {
            ConsultationDTO createdConsultation = doctorService.createConsultation(appointmentID, consultationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdConsultation);
        } catch (DuplicateRecordException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // ✅ Get Consultations by Doctor
    @GetMapping("/{doctorID}/consultations")
    public ResponseEntity<List<ConsultationDTO>> getConsultationsByDoctor(@PathVariable Long doctorID) {
        List<ConsultationDTO> consultationList = doctorService.getConsultationsByDoctor(doctorID);
        return ResponseEntity.ok(consultationList);
    }

    // ✅ Get Consultation by ID
    @GetMapping("/consultations/{consultationId}")
    public ResponseEntity<ConsultationDTO> getConsultationById(@PathVariable Long consultationId) {
        ConsultationDTO consultation = doctorService.getConsultationById(consultationId);
        return ResponseEntity.ok(consultation);
    }

    // ✅ Update Consultation Notes
    @PutMapping("/consultations/{consultationId}/notes")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ConsultationDTO> updateConsultation(@PathVariable Long consultationId,
                                                              @RequestParam String notes) {
        ConsultationDTO updatedConsultation = doctorService.updateConsultation(consultationId, notes);
        return ResponseEntity.ok(updatedConsultation);
    }

    // ✅ Delete Consultation
    @DeleteMapping("/consultations/{consultationId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long consultationId) {
        doctorService.deleteConsultation(consultationId);
        return ResponseEntity.noContent().build();
    }
}
