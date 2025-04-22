package com.cognizant.healthcare.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
 
import com.cognizant.healthcare.repository.DoctorRepository;
import com.cognizant.healthcare.repository.PatientRepository;
import com.cognizant.healthcare.repository.UserRepository;
import com.cognizant.healthcare.util.JwtUtil;
import com.cognizant.healthcare.DTO.AuthenticationRequestDTO;
import com.cognizant.healthcare.DTO.RegistrationRequestDTO;
import com.cognizant.healthcare.entity.User;
import com.cognizant.healthcare.entity.Doctor;
import com.cognizant.healthcare.entity.Patient;

@Service
public class UserService 
{
 
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DoctorRepository doctorRepository;
 
    @Autowired
    private PatientRepository patientRepository;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
 
    public User saveUser(RegistrationRequestDTO request) {
        // Save user details in the User table
        User user = new User();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt password
        user.setRole(request.getRole());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        User savedUser = userRepository.save(user);
 
        // Save role-specific details
        if ("ROLE_DOCTOR".equalsIgnoreCase(request.getRole())) {
            Doctor doctor = new Doctor();
            doctor.setUser(savedUser); // Associate the doctor with the user
            doctor.setQualification(request.getQualification());
            doctor.setSpecialization(request.getSpecialization());
            doctor.setExperienceYears(request.getExperienceYears());
            doctor.setStatus(request.getStatus());
            doctorRepository.save(doctor);
            savedUser.setDoctor(doctor);
        } else if ("ROLE_PATIENT".equalsIgnoreCase(request.getRole())) {
            Patient patient = new Patient();
            patient.setUser(savedUser); // Associate the patient with the user
            patient.setAge(request.getAge());
            patient.setGender(request.getGender());
            patient.setCurrentMedications(request.getCurrentMedications());
            patient.setMedicalHistory(request.getMedicalHistory());
            patientRepository.save(patient);
            savedUser.setPatient(patient);
        }
 
        return userRepository.save(savedUser);
    }
 
    public String authenticateUser(AuthenticationRequestDTO request) {
        // Strictly handle login logic
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())//class
        );
 
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream().findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
 
        String token = jwtUtil.generateToken(request.getName(), role);
        return "Login successful! Token: " + token;
    }
}
 
 
 