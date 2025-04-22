package com.cognizant.healthcare.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.cognizant.healthcare.DTO.*;
import com.cognizant.healthcare.entity.*;
import com.cognizant.healthcare.exception.*;
import com.cognizant.healthcare.repository.*;
import com.cognizant.healthcare.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private Availability availability;
    private Appointment appointment;
    private Consultation consultation;
    private DoctorDTO doctorDTO;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserID(1L);
        user.setName("Dr. Smith");
        user.setPassword("password123");

        userDTO = new UserDTO();
        userDTO.setUserID(1L);
        userDTO.setName("Dr. Smith");

        doctor = new Doctor();
        doctor.setDoctorID(1L);
        doctor.setUser(user);

        doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorID(1L);
        doctorDTO.setUser(userDTO); // Fix: Assign `UserDTO`

        availability = new Availability();
        availability.setDoctor(doctor);

        appointment = new Appointment();
        appointment.setDoctor(doctor);

        consultation = new Consultation();
        consultation.setDoctor(doctor);
    }

    // **Test Creating a Doctor**
    @Test
    void testCreateDoctor() {
        when(modelMapper.map(doctorDTO.getUser(), User.class)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(doctorDTO, Doctor.class)).thenReturn(doctor);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        when(modelMapper.map(doctor, DoctorDTO.class)).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.createDoctor(doctorDTO);

        assertNotNull(result);
        assertNotNull(result.getUser());
        assertEquals("Dr. Smith", result.getUser().getName()); // Fix: Validate `UserDTO`
        verify(userRepository, times(1)).save(any(User.class));
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    // **Test Getting a Doctor by ID**
    @Test
    void testGetDoctorById_Success() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(modelMapper.map(doctor, DoctorDTO.class)).thenReturn(doctorDTO);

        Optional<DoctorDTO> result = doctorService.getDoctorByID(1L);

        assertTrue(result.isPresent());
        assertEquals(doctor.getDoctorID(), result.get().getDoctorID());
    }

    @Test
    void testGetDoctorById_NotFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> doctorService.getDoctorByID(1L));
    }

    // **Test Updating a Doctor**
    @Test
    void testUpdateDoctor() {
        DoctorDTO updatedDoctorDTO = new DoctorDTO();
        updatedDoctorDTO.setDoctorID(1L);

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        when(modelMapper.map(doctor, DoctorDTO.class)).thenReturn(updatedDoctorDTO);

        Optional<DoctorDTO> result = doctorService.updateDoctor(1L, updatedDoctorDTO);

        assertTrue(result.isPresent());
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    // **Test Deleting a Doctor**
    @Test
    void testDeleteDoctor() {
        when(doctorRepository.existsById(1L)).thenReturn(true);
        doctorService.deleteDoctor(1L);
        verify(doctorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDoctor_NotFound() {
        when(doctorRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> doctorService.deleteDoctor(1L));
    }

    // **Test Creating Availability**
    @Test
    void testCreateAvailability() {
        AvailabilityDTO availabilityDTO = new AvailabilityDTO();
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(modelMapper.map(availabilityDTO, Availability.class)).thenReturn(availability);
        when(availabilityRepository.save(any(Availability.class))).thenReturn(availability);
        when(modelMapper.map(availability, AvailabilityDTO.class)).thenReturn(availabilityDTO);

        AvailabilityDTO result = doctorService.createDoctorAvailability(1L, availabilityDTO);

        assertNotNull(result);
        verify(availabilityRepository, times(1)).save(any(Availability.class));
    }

    // **Test Getting Appointments by Doctor ID**
    @Test
    void testGetAppointmentsByDoctorId() {
        when(doctorRepository.existsById(1L)).thenReturn(true);
        when(appointmentRepository.findByDoctor_DoctorID(1L)).thenReturn(List.of(appointment));
        when(modelMapper.map(any(Appointment.class), eq(AppointmentDTO.class))).thenReturn(new AppointmentDTO());

        List<AppointmentDTO> result = doctorService.getAppointmentsByDoctorId(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // **Test Creating Consultation**
    @Test
    void testCreateConsultation() {
        ConsultationDTO consultationDTO = new ConsultationDTO();
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(consultationRepository.findByAppointment_AppointmentID(1L)).thenReturn(Optional.empty());
        when(modelMapper.map(consultationDTO, Consultation.class)).thenReturn(consultation);
        when(consultationRepository.save(any(Consultation.class))).thenReturn(consultation);
        when(modelMapper.map(consultation, ConsultationDTO.class)).thenReturn(consultationDTO);

        ConsultationDTO result = doctorService.createConsultation(1L, consultationDTO);

        assertNotNull(result);
    }

    @Test
    void testCreateConsultation_Duplicate() {
        ConsultationDTO consultationDTO = new ConsultationDTO();
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(consultationRepository.findByAppointment_AppointmentID(1L)).thenReturn(Optional.of(consultation));

        assertThrows(DuplicateRecordException.class, () -> doctorService.createConsultation(1L, consultationDTO));
    }

    // **Test Deleting Consultation**
    @Test
    void testDeleteConsultation() {
        when(consultationRepository.findById(1L)).thenReturn(Optional.of(consultation));
        doctorService.deleteConsultation(1L);
        verify(consultationRepository, times(1)).delete(any(Consultation.class));
    }
}
