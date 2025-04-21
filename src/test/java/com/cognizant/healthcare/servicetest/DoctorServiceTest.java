package com.cognizant.healthcare.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cognizant.healthcare.DTO.AppointmentDTO;
import com.cognizant.healthcare.DTO.AvailabilityDTO;
import com.cognizant.healthcare.DTO.ConsultationDTO;
import com.cognizant.healthcare.DTO.DoctorDTO;
import com.cognizant.healthcare.DTO.UserDTO;
import com.cognizant.healthcare.entity.Appointment;
import com.cognizant.healthcare.entity.Availability;
import com.cognizant.healthcare.entity.Consultation;
import com.cognizant.healthcare.entity.Doctor;
import com.cognizant.healthcare.entity.User;
import com.cognizant.healthcare.repository.AppointmentRepository;
import com.cognizant.healthcare.repository.AvailabilityRepository;
import com.cognizant.healthcare.repository.ConsultationRepository;
import com.cognizant.healthcare.repository.DoctorRepository;
import com.cognizant.healthcare.repository.PatientRepository;
import com.cognizant.healthcare.repository.UserRepository;
import com.cognizant.healthcare.service.DoctorService;

class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Doctor doctor;
    private User user;
    private DoctorDTO doctorDTO;
    private Availability availability;
    private AvailabilityDTO availabilityDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserID(1L);
        user.setName("Dr. Smith");

        doctor = new Doctor();
        doctor.setDoctorID(1L);
        doctor.setUser(user);
        doctor.setQualification("MBBS");
        doctor.setSpecialization("Cardiology");
        doctor.setExperienceYears(10);
        doctor.setStatus("Active");

        doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorID(1L);
        doctorDTO.setUser(new UserDTO());
        doctorDTO.setQualification("MBBS");
        doctorDTO.setSpecialization("Cardiology");
        doctorDTO.setExperienceYears(10);
        doctorDTO.setStatus("Active");

        availability = new Availability();
        availability.setAvailableDay(com.cognizant.healthcare.constants.DayOfWeek.MONDAY);
        availability.setTimeslot("10:00 AM");
        availability.setDoctor(doctor);

        availabilityDTO = new AvailabilityDTO();
        availabilityDTO.setAvailableDay(com.cognizant.healthcare.constants.DayOfWeek.MONDAY);
        availabilityDTO.setTimeslot("10:00 AM");
    }

    @Test
    void testCreateDoctor() {
        when(modelMapper.map(doctorDTO.getUser(), User.class)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(doctorDTO, Doctor.class)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(modelMapper.map(doctor, DoctorDTO.class)).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.createDoctor(doctorDTO);

        assertNotNull(result);
        assertEquals("Cardiology", result.getSpecialization());
        verify(userRepository, times(1)).save(user);
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void testGetDoctorByID() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(modelMapper.map(doctor, DoctorDTO.class)).thenReturn(doctorDTO);

        Optional<DoctorDTO> result = doctorService.getDoctorByID(1L);

        assertTrue(result.isPresent());
        assertEquals("MBBS", result.get().getQualification());
    }

    @Test
    void testUpdateDoctor() {
        DoctorDTO updatedDTO = new DoctorDTO();
        updatedDTO.setQualification("MD");
        updatedDTO.setSpecialization("Neurology");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(modelMapper.map(doctor, DoctorDTO.class)).thenReturn(updatedDTO);

        Optional<DoctorDTO> result = doctorService.updateDoctor(1L, updatedDTO);

        assertTrue(result.isPresent());
        assertEquals("Neurology", result.get().getSpecialization());
    }

    @Test
    void testDeleteDoctor() {
        doNothing().when(doctorRepository).deleteById(1L);

        doctorService.deleteDoctor(1L);

        verify(doctorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCreateDoctorAvailability() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(modelMapper.map(availabilityDTO, Availability.class)).thenReturn(availability);
        when(availabilityRepository.save(availability)).thenReturn(availability);
        when(modelMapper.map(availability, AvailabilityDTO.class)).thenReturn(availabilityDTO);

        AvailabilityDTO result = doctorService.createDoctorAvailability(1L, availabilityDTO);

        assertNotNull(result);
        assertEquals("10:00 AM", result.getTimeslot());
    }

    @Test
    void testGetDoctorAvailability() {
        when(availabilityRepository.findByDoctor_DoctorID(1L)).thenReturn(List.of(availability));
        when(modelMapper.map(availability, AvailabilityDTO.class)).thenReturn(availabilityDTO);

        List<AvailabilityDTO> result = doctorService.getDoctorAvailability(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetAppointmentsByDoctorId() {
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);

        when(appointmentRepository.findByDoctor_DoctorID(1L)).thenReturn(List.of(appointment));
        when(modelMapper.map(appointment, AppointmentDTO.class)).thenReturn(new AppointmentDTO());

        List<AppointmentDTO> result = doctorService.getAppointmentsByDoctorId(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetConsultationsByDoctor() {
        Consultation consultation = new Consultation();
        consultation.setDoctor(doctor);

        when(consultationRepository.findByDoctor_DoctorID(1L)).thenReturn(List.of(consultation));
        when(modelMapper.map(consultation, ConsultationDTO.class)).thenReturn(new ConsultationDTO());

        List<ConsultationDTO> result = doctorService.getConsultationsByDoctor(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
