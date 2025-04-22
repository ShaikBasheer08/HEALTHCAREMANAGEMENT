package com.cognizant.healthcare.controller;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import com.cognizant.healthcare.DTO.AppointmentDTO;
import com.cognizant.healthcare.DTO.AppointmentResponseDTO;
import com.cognizant.healthcare.DTO.ConsultationDTO;
import com.cognizant.healthcare.DTO.ConsultationDetailsDTO;
import com.cognizant.healthcare.DTO.PatientDTO;
import com.cognizant.healthcare.service.PatientService;
 
import jakarta.validation.Valid;
 
 
 
 
@RestController
@RequestMapping("/patients")
public class PatientController {
 
    @Autowired
    private PatientService patientService;
   
// Create a new patient
 
    @PostMapping("/add")
 
    @PreAuthorize("hasRole('PATIENT')")
 
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
 
        PatientDTO createdPatient = patientService.createPatient(patientDTO);
 
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
 
    }
 
    // Get a patient by ID
 
    @GetMapping("/{id}")
 
    @PreAuthorize("hasRole('PATIENT')")
 
    public ResponseEntity<PatientDTO> getPatientByID(@Valid @PathVariable Long id) {
 
        return patientService.getPatientByID(id)
 
                .map(ResponseEntity::ok)
 
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
 
    }
 
    // Update patient details
 
    @PutMapping("/{id}")
 
   @PreAuthorize("hasRole('PATIENT')")
 
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id,@Valid @RequestBody PatientDTO patientDTO) {
 
        return patientService.updatePatient(id, patientDTO)
 
                .map(ResponseEntity::ok)
 
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
 
    }
 
    // Delete a patient by ID
 
    @DeleteMapping("/{id}")
 
   @PreAuthorize("hasRole('PATIENT')")
 
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
 
        patientService.deletePatient(id);
 
        return ResponseEntity.noContent().build();
 
    }
 
    // Book an appointment
 
    @PostMapping("/{patientID}/appointments")
 
    @PreAuthorize("hasRole('PATIENT')")
 
    public ResponseEntity<AppointmentDTO> bookAppointment(
 
            @PathVariable Long patientID,
 
           @Valid @RequestParam Long doctorID,
 
           @Valid  @RequestParam LocalDate date,
 
           @Valid  @RequestParam String timeslot) {
 
        AppointmentDTO appointmentDTO = patientService.bookAppointment(patientID, doctorID, date, timeslot);
 
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentDTO);
 
    }
// Retrieve appointments by patient ID
    @GetMapping("/{patientID}/appointments")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPatientID(@PathVariable Long patientID) {
        List<AppointmentResponseDTO> appointmentResponseDTOs = patientService.getAppointmentsByPatientID(patientID);
        return ResponseEntity.ok(appointmentResponseDTOs);
    }
 
    // View consultation history
    @GetMapping("/{patientID}/consultations/history")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<ConsultationDTO>> getConsultationHistory(@PathVariable Long patientID) {
        List<ConsultationDTO> consultationHistory = patientService.getConsultationHistory(patientID);
        return ResponseEntity.ok(consultationHistory);
    }

   
 
 
 
    
    @GetMapping("/{patientID}/consultations/{consultationID}/details")
    public ResponseEntity<ConsultationDetailsDTO> getConsultationDetails(@PathVariable Long consultationID) {
        ConsultationDetailsDTO consultationDetailsDTO = patientService.getConsultationDetails(consultationID);
        return ResponseEntity.ok(consultationDetailsDTO);
    }

 
    @GetMapping("/{patientID}/notification")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<String> getNotificationById(@PathVariable Long patientID) {
        String response = patientService.getNotificationById(patientID);
        return ResponseEntity.ok(response);
    }

}
 
 
 
 