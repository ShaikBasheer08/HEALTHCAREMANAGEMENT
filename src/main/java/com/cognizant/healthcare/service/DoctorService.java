package com.cognizant.healthcare.service;
import com.cognizant.healthcare.DTO.AvailabilityDTO;
import com.cognizant.healthcare.DTO.ConsultationDTO;
import com.cognizant.healthcare.DTO.DoctorDTO;
import com.cognizant.healthcare.entity.Appointment;
import com.cognizant.healthcare.entity.Availability;
import com.cognizant.healthcare.entity.Consultation;
import com.cognizant.healthcare.entity.Doctor;
import com.cognizant.healthcare.entity.Patient;
import com.cognizant.healthcare.entity.User;
import com.cognizant.healthcare.exception.ResourceNotFoundException;
import com.cognizant.healthcare.repository.AppointmentRepository;
import com.cognizant.healthcare.repository.AvailabilityRepository;
import com.cognizant.healthcare.repository.ConsultationRepository;
import com.cognizant.healthcare.repository.DoctorRepository;
import com.cognizant.healthcare.repository.PatientRepository;
import com.cognizant.healthcare.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create a new doctor
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        User user = modelMapper.map(doctorDTO.getUser(), User.class);
        User savedUser = userRepository.save(user);

        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
        doctor.setUser(savedUser);

        Doctor savedDoctor = doctorRepository.save(doctor);
        return modelMapper.map(savedDoctor, DoctorDTO.class);
    }

    // Get a doctor by ID
    public Optional<DoctorDTO> getDoctorById(Long doctorID) {
        return doctorRepository.findById(doctorID)
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class));
    }

    // Update doctor details
    public Optional<DoctorDTO> updateDoctor(Long doctorID, DoctorDTO updatedDoctorDTO) {
        return doctorRepository.findById(doctorID).map(existingDoctor -> {
            modelMapper.map(updatedDoctorDTO, existingDoctor);
            Doctor updatedDoctor = doctorRepository.save(existingDoctor);
            return modelMapper.map(updatedDoctor, DoctorDTO.class);
        });
    }

    // Delete a doctor by ID
    public void deleteDoctor(Long doctorID) {
        doctorRepository.deleteById(doctorID);
    }

    // Create availability for a doctor
    public AvailabilityDTO createDoctorAvailability(Long doctorID, AvailabilityDTO availabilityDTO) {
        Doctor doctor = doctorRepository.findById(doctorID)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Availability availability = modelMapper.map(availabilityDTO, Availability.class);
        availability.setDoctor(doctor);

        Availability savedAvailability = availabilityRepository.save(availability);
        return modelMapper.map(savedAvailability, AvailabilityDTO.class);
    }

    // Get availability by doctor ID
    public List<AvailabilityDTO> getDoctorAvailability(Long doctorID) {
        List<Availability> availabilities = availabilityRepository.findByDoctor_DoctorId(doctorID);
        return availabilities.stream()
                .map(availability -> modelMapper.map(availability, AvailabilityDTO.class))
                .collect(Collectors.toList());
    }

    // **Consultation Logic**

 // **Consultation Logic**

    // Create a consultation based on appointment ID
    public ConsultationDTO createConsultation(Long appointmentID, ConsultationDTO consultationDTO) {
        // Fetch the appointment
        Appointment appointment = appointmentRepository.findById(appointmentID)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        // Ensure the consultation does not already exist for this appointment
        if (consultationRepository.findByAppointment_AppointmentID(appointmentID).isPresent()) {
            throw new IllegalStateException("Consultation already exists for this appointment");
        }

        // Create consultation
        Consultation consultation = modelMapper.map(consultationDTO, Consultation.class);
        consultation.setAppointment(appointment);
        consultation.setDoctor(appointment.getDoctor());
        consultation.setConsultationDate(consultationDTO.getConsultationDate());

        consultation.setPatient(appointment.getPatient());

        Consultation savedConsultation = consultationRepository.save(consultation);
        return modelMapper.map(savedConsultation, ConsultationDTO.class);
    }

    // Get consultations for a doctor
    public List<ConsultationDTO> getConsultationsByDoctor(Long doctorId) {
        List<Consultation> consultations = consultationRepository.findByDoctor_DoctorId(doctorId);
        return consultations.stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDTO.class))
                .collect(Collectors.toList());
    }

    // Get a consultation by ID
    public ConsultationDTO getConsultationById(Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found"));
        return modelMapper.map(consultation, ConsultationDTO.class);
    }

    // Update consultation notes
    public ConsultationDTO updateConsultation(Long consultationId, String notes) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found"));

        consultation.setNotes(notes);
        Consultation updatedConsultation = consultationRepository.save(consultation);
        return modelMapper.map(updatedConsultation, ConsultationDTO.class);
    }

    // Delete a consultation
    public void deleteConsultation(Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found"));
        consultationRepository.delete(consultation);
    }
}


