package com.cognizant.healthcare.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
 
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
//import com.cognizant.healthcare.repository.UserRepository;
import com.cognizant.healthcare.DTO.AuthenticationRequestDTO;
import com.cognizant.healthcare.DTO.AuthenticationResponseDTO;
import com.cognizant.healthcare.DTO.RegistrationRequestDTO;
import com.cognizant.healthcare.entity.User;
import com.cognizant.healthcare.service.UserService;
import com.cognizant.healthcare.security.MyUserDetailsService;
import com.cognizant.healthcare.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {
 
    @Autowired
    private UserService authenticationService;
 
    // Save user endpoint (registration)
    @PostMapping("/saveUser")
    public User register(@Valid @RequestBody RegistrationRequestDTO request) {
        return authenticationService.saveUser(request); // Delegate to service
    }
 
    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequestDTO request) {
        try {
            String response = authenticationService.authenticateUser(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed: " + ex.getMessage());
        }
    }
}
 
 
 