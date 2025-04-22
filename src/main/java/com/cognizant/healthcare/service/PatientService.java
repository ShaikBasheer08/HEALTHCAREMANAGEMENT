package com.cognizant.healthcare.service;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.cognizant.healthcare.DTO.AppointmentDTO;
import com.cognizant.healthcare.DTO.AppointmentResponseDTO;
import com.cognizant.healthcare.DTO.ConsultationDTO;
import com.cognizant.healthcare.DTO.ConsultationDetailsDTO;
import com.cognizant.healthcare.DTO.DoctorInfoDTO;
import com.cognizant.healthcare.DTO.PatientDTO;
import com.cognizant.healthcare.DTO.PatientInfoDTO;
//import com.cognizant.healthcare.DTO.UserDTO;
import com.cognizant.healthcare.constants.DayOfWeek;
import com.cognizant.healthcare.entity.Appointment;
import com.cognizant.healthcare.entity.Consultation;
import com.cognizant.healthcare.entity.Doctor;
import com.cognizant.healthcare.entity.Patient;
import com.cognizant.healthcare.entity.User;
import com.cognizant.healthcare.exception.ResourceNotFoundException;
import com.cognizant.healthcare.exception.DuplicateRecordException;
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
    private ModelMapper modelMapper;
 
    // Create a new patient
    public PatientDTO createPatient(PatientDTO patientDTO) {
        User user = modelMapper.map(patientDTO.getUser(), User.class);
        User savedUser = userRepository.save(user);

        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setUser(savedUser);

        Patient savedPatient = patientRepository.save(patient);
        return modelMapper.map(savedPatient, PatientDTO.class);
    }
 
    // Get a patient by ID (returns Optional)
    public Optional<PatientDTO> getPatientByID(Long patientID) {
        return Optional.of(patientRepository.findById(patientID)
            .map(patient -> modelMapper.map(patient, PatientDTO.class))
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + patientID + " not found")));
    }

    // Update patient details (uses Optional)
    public Optional<PatientDTO> updatePatient(Long patientID, PatientDTO updatedPatientDTO) {
        return Optional.of(patientRepository.findById(patientID)
            .map(existingPatient -> {
                modelMapper.map(updatedPatientDTO, existingPatient);
                Patient updatedPatient = patientRepository.save(existingPatient);
                return modelMapper.map(updatedPatient, PatientDTO.class);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + patientID + " not found")));
    }
 
    // Delete a patient by ID
    public void deletePatient(Long patientID) {
        if (!patientRepository.existsById(patientID)) {
            throw new ResourceNotFoundException("Patient with ID " + patientID + " not found");
        }
        patientRepository.deleteById(patientID);
    }

    // Book appointment with proper validation
    public AppointmentDTO bookAppointment(Long patientID, Long doctorID, LocalDate date, String timeslot) {
        Patient patient = patientRepository.findById(patientID)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID " + patientID + " not found"));
        Doctor doctor = doctorRepository.findById(doctorID)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + doctorID + " not found"));

        DayOfWeek day = DayOfWeek.valueOf(date.getDayOfWeek().name());

        // Validate doctor's availability
        boolean isAvailable = doctor.getAvailabilityList().stream()
                .anyMatch(availability -> availability.getAvailableDay().equals(day)
                                          && availability.getTimeslot().equals(timeslot));
        if (!isAvailable) {
            throw new ResourceNotFoundException("Doctor is not available on " + day + " at timeslot " + timeslot);
        }

        // Check for patient conflicts in appointments
        boolean patientConflict = appointmentRepository.findByPatient_PatientID(patientID).stream()
                .anyMatch(appointment -> appointment.getDate().equals(date)
                                          && appointment.getTimeslot().equals(timeslot));
        if (patientConflict) {
            throw new DuplicateRecordException("Patient already has an appointment at this date and timeslot");
        }

        // Check for doctor conflicts in appointments
        boolean doctorConflict = appointmentRepository.findByDoctor_DoctorID(doctorID).stream()
                .anyMatch(appointment -> appointment.getDate().equals(date)
                                          && appointment.getTimeslot().equals(timeslot));
        if (doctorConflict) {
            throw new DuplicateRecordException("Doctor already has an appointment at this date and timeslot");
        }

        // Book appointment
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(date);
        appointment.setTimeslot(timeslot);
        appointment.setStatus("Booked");

        return modelMapper.map(appointmentRepository.save(appointment), AppointmentDTO.class);
    }

    // Retrieve appointments by patient ID
    public List<AppointmentResponseDTO> getAppointmentsByPatientID(Long patientID) {
        if (!patientRepository.existsById(patientID)) {
            throw new ResourceNotFoundException("Patient with ID " + patientID + " not found");
        }
        List<Appointment> appointments = appointmentRepository.findByPatient_PatientID(patientID);
        return appointments.stream()
                .map(appointment -> {
                    AppointmentResponseDTO responseDTO = modelMapper.map(appointment, AppointmentResponseDTO.class);

                    // Map Patient details without User
                    PatientInfoDTO patientInfoDTO = new PatientInfoDTO();
                    patientInfoDTO.setPatientID(appointment.getPatient().getPatientID());
                    patientInfoDTO.setName(appointment.getPatient().getUser().getName());
                    patientInfoDTO.setAge(appointment.getPatient().getAge());
                    patientInfoDTO.setGender(appointment.getPatient().getGender());
                    responseDTO.setPatient(patientInfoDTO);

                    // Map Doctor details without User
                    DoctorInfoDTO doctorInfoDTO = new DoctorInfoDTO();
                    doctorInfoDTO.setDoctorID(appointment.getDoctor().getDoctorID());
                    doctorInfoDTO.setName(appointment.getDoctor().getUser().getName());
                    doctorInfoDTO.setQualification(appointment.getDoctor().getQualification());
                    doctorInfoDTO.setSpecialization(appointment.getDoctor().getSpecialization());
                    responseDTO.setDoctor(doctorInfoDTO);

                    return responseDTO;
                })
                .collect(Collectors.toList());
    }

    // View consultation history for a patient
    public List<ConsultationDTO> getConsultationHistory(Long patientID) {
        if (!patientRepository.existsById(patientID)) {
            throw new ResourceNotFoundException("Patient with ID " + patientID + " not found");
        }
        return consultationRepository.findByAppointment_Patient_PatientID(patientID)
                .stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDTO.class))
                .collect(Collectors.toList());
    }
    
    public ConsultationDetailsDTO getConsultationDetails(Long consultationID) {
        Consultation consultation = consultationRepository.findById(consultationID)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found"));

        return modelMapper.map(consultation, ConsultationDetailsDTO.class);
    }

    public String getNotificationById(Long patientID) {
		if(appointmentRepository.existsByPatientPatientID(patientID))
		{
			return "Appointment Booked Successfully";
		}
		return "No Appointment Found !";
		
	}

}
