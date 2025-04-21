package com.cognizant.healthcare.servicetest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.cognizant.healthcare.DTO.AppointmentDTO;
import com.cognizant.healthcare.DTO.AppointmentResponseDTO;
import com.cognizant.healthcare.DTO.ConsultationDTO;
import com.cognizant.healthcare.DTO.PatientDTO;
import com.cognizant.healthcare.DTO.UserDTO;
import com.cognizant.healthcare.constants.DayOfWeek;
import com.cognizant.healthcare.entity.Appointment;
import com.cognizant.healthcare.entity.Availability;
import com.cognizant.healthcare.entity.Consultation;
import com.cognizant.healthcare.entity.Doctor;
import com.cognizant.healthcare.entity.Patient;
import com.cognizant.healthcare.entity.User;
import com.cognizant.healthcare.repository.AppointmentRepository;
import com.cognizant.healthcare.repository.ConsultationRepository;
import com.cognizant.healthcare.repository.DoctorRepository;
import com.cognizant.healthcare.repository.PatientRepository;
import com.cognizant.healthcare.repository.UserRepository;
import com.cognizant.healthcare.service.PatientService;

class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private ModelMapper modelMapper;

    private Patient patient;
    private Doctor doctor;
    private Appointment appointment;
    private Consultation consultation;
    private PatientDTO patientDTO;
    private User user;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserID(1L);
        user.setName("Dr. Smith");

        doctor = new Doctor();
        doctor.setDoctorID(1L);
        doctor.setUser(user);

        // 🔥 Ensure that Availability has a valid day and timeslot
        Availability availability = new Availability();
        availability.setAvailableDay(DayOfWeek.SUNDAY);  // ✅ Set proper day
        availability.setTimeslot("10:00 AM");
        availability.setDoctor(doctor);

        doctor.setAvailabilityList(List.of(availability)); // ✅ Assign initialized Availability object

     
        patientDTO = new PatientDTO();
        patientDTO.setPatientID(1L);
        patientDTO.setAge(30);
        patientDTO.setGender("Male");
        patientDTO.setUser(new UserDTO());

        patient = new Patient();
        patient.setPatientID(1L);
        patient.setAge(30);
        patient.setGender("Male");
        patient.setUser(user);


        appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(LocalDate.now().plusDays(1)); 
        appointment.setTimeslot("10:00 AM");



     
        consultation = new Consultation();
    }

    @Test
    void testCreatePatient() {
        when(modelMapper.map(patientDTO.getUser(), User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(patientDTO, Patient.class)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(modelMapper.map(patient, PatientDTO.class)).thenReturn(patientDTO);

        PatientDTO result = patientService.createPatient(patientDTO);

        assertNotNull(result);
        assertEquals(30, result.getAge());
        verify(userRepository, times(1)).save(user);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void testGetPatientByID() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(modelMapper.map(patient, PatientDTO.class)).thenReturn(patientDTO);

        Optional<PatientDTO> result = patientService.getPatientByID(1L);

        assertTrue(result.isPresent());
        assertEquals(30, result.get().getAge());
    }

    @Test
    void testUpdatePatient() {
        PatientDTO updatedDTO = new PatientDTO();
        updatedDTO.setAge(35);
        updatedDTO.setGender("Male");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);
        when(modelMapper.map(patient, PatientDTO.class)).thenReturn(updatedDTO);

        Optional<PatientDTO> result = patientService.updatePatient(1L, updatedDTO);

        assertTrue(result.isPresent());
        assertEquals(35, result.get().getAge());
    }


    @Test
    void testDeletePatient() {
        doNothing().when(patientRepository).deleteById(1L);

        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).deleteById(1L);
    }

//    @Test
//    void testBookAppointment() {
//        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
//        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
//        when(appointmentRepository.findByPatient_PatientID(1L)).thenReturn(Collections.emptyList());
//        when(appointmentRepository.findByDoctor_DoctorID(1L)).thenReturn(Collections.emptyList());
//        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
//        when(modelMapper.map(appointment, AppointmentDTO.class)).thenReturn(new AppointmentDTO());
//
//        AppointmentDTO result = patientService.bookAppointment(1L, 1L, LocalDate.now().plusDays(1), "10:00 AM");
//
//        assertNotNull(result);
//        verify(appointmentRepository, times(1)).save(any(Appointment.class));
//    }

    @Test
    void testGetAppointmentsByPatientID() {
        when(appointmentRepository.findByPatient_PatientID(1L)).thenReturn(List.of(appointment));
        when(modelMapper.map(appointment, AppointmentResponseDTO.class)).thenReturn(new AppointmentResponseDTO());

        List<AppointmentResponseDTO> result = patientService.getAppointmentsByPatientID(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetConsultationHistory() {
        when(consultationRepository.findByAppointment_Patient_PatientID(1L)).thenReturn(List.of(consultation));
        when(modelMapper.map(consultation, ConsultationDTO.class)).thenReturn(new ConsultationDTO());

        List<ConsultationDTO> result = patientService.getConsultationHistory(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
