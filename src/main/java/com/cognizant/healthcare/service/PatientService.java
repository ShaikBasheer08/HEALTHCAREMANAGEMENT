package com.cognizant.healthcare.service;
 
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.cognizant.healthcare.DTO.AppointmentDTO;
import com.cognizant.healthcare.DTO.ConsultationDTO;
import com.cognizant.healthcare.DTO.PatientDTO;
import com.cognizant.healthcare.DTO.UserDTO;
import com.cognizant.healthcare.constants.DayOfWeek;
import com.cognizant.healthcare.entity.Appointment;
import com.cognizant.healthcare.entity.Consultation;
import com.cognizant.healthcare.entity.Doctor;
import com.cognizant.healthcare.entity.Patient;
import com.cognizant.healthcare.entity.User;
import com.cognizant.healthcare.repository.AppointmentRepository;
import com.cognizant.healthcare.repository.ConsultationRepository;
import com.cognizant.healthcare.repository.DoctorRepository;
import com.cognizant.healthcare.repository.PatientRepository;
import com.cognizant.healthcare.repository.UserRepository;
 
 
 
@Service
public class PatientService {
 
    @Autowired
    private PatientRepository patientRepository;
 
    @Autowired
    private AppointmentRepository appointmentRepository;
 
    @Autowired
    private ConsultationRepository consultationRepository;
 
    @Autowired
    private DoctorRepository doctorRepository;
 
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private ModelMapper modelMapper; // ModelMapper instance for mapping entities and DTOs
 
    // Create a new patient
 
    public PatientDTO createPatient(PatientDTO patientDTO) {
        // Map UserDTO to User entity
        User user = modelMapper.map(patientDTO.getUser(), User.class);
        User savedUser = userRepository.save(user);
 
        // Map PatientDTO to Patient entity
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setUser(savedUser);
 
        // Save Patient entity and return mapped PatientDTO
        Patient savedPatient = patientRepository.save(patient);
        return modelMapper.map(savedPatient, PatientDTO.class);
    }
 
    // Get a patient by ID (returns Optional)
 
    public Optional<PatientDTO> getPatientByID(Long patientID) {
        return patientRepository.findByPatientID(patientID)
                .map(patient -> modelMapper.map(patient, PatientDTO.class));
    }
 
    // Update patient details (uses Optional)
 
    public Optional<PatientDTO> updatePatient(Long patientID, PatientDTO updatedPatientDTO) {
        return patientRepository.findById(patientID).map(existingPatient -> {
            modelMapper.map(updatedPatientDTO, existingPatient); // Map updated fields
            Patient updatedPatient = patientRepository.save(existingPatient);
            return modelMapper.map(updatedPatient, PatientDTO.class);
        });
    }
 
    // Delete a patient by ID
  
    public void deletePatient(Long patientID) {
        patientRepository.deleteById(patientID);
    }
 
    
    
    
    public AppointmentDTO bookAppointment(Long patientID, Long doctorID, LocalDate date, String timeslot) {
        // Step 1: Find patient and doctor details
        Patient patient = patientRepository.findById(patientID)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(doctorID)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
 
        // Step 2: Generate the day of the week from the date
        DayOfWeek day = DayOfWeek.valueOf(date.getDayOfWeek().name());
 
        // Step 3: Validate doctor's availability based on day and timeslot
        boolean isAvailable = doctor.getAvailabilityList().stream()
                .anyMatch(availability -> availability.getAvailableDay().equals(day)
                                          && availability.getTimeslot().equals(timeslot));
        if (!isAvailable) {
            throw new RuntimeException("Doctor is not available on " + day + " at the requested timeslot: " + timeslot);
        }
 
        // Step 4: Check for patient conflicts in appointments
        boolean patientConflict = appointmentRepository.findByPatient_PatientID(patientID).stream()
                .anyMatch(appointment -> appointment.getDate().equals(date)
                                          && appointment.getTimeslot().equals(timeslot));
        if (patientConflict) {
            throw new RuntimeException("Patient already has an appointment at this date and timeslot");
        }
 
        // Step 5: Check for doctor conflicts in appointments
        boolean doctorConflict = appointmentRepository.findByDoctor_DoctorID(doctorID).stream()
                .anyMatch(appointment -> appointment.getDate().equals(date)
                                          && appointment.getTimeslot().equals(timeslot));
        if (doctorConflict) {
            throw new RuntimeException("Doctor already has an appointment at this date and timeslot");
        }
 
        // Step 6: Book appointment
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(date);
        appointment.setTimeslot(timeslot);
        appointment.setStatus("Booked");
 
        // Step 7: Save and map to DTO
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return modelMapper.map(savedAppointment, AppointmentDTO.class);
    }
 
 
 
    // Retrieve appointments by patient ID
    public List<AppointmentDTO> getAppointmentsByPatientID(Long patientID) {
        return appointmentRepository.findByPatient_PatientID(patientID)
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }
 
  //View consultation history for a patient
    public List<ConsultationDTO> getConsultationHistory(Long patientID) {
        return consultationRepository.findByAppointment_Patient_PatientID(patientID)
                .stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDTO.class))
                .collect(Collectors.toList());
    }
    
 
        
    }
 
 
 
 
 