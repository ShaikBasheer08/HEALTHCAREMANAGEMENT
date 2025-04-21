package com.cognizant.healthcare.security;
 
import java.util.Collections;
 
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.cognizant.healthcare.repository.UserRepository;

import  com.cognizant.healthcare.entity.User;
 
@Service

public class MyUserDetailsService implements UserDetailsService {
 
	@Autowired

	UserRepository userRepository;
 
	

	    @Override

	    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

	        User user = userRepository.findByName(name)

	                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + name));
 
	        return new org.springframework.security.core.userdetails.User

	        		(user.getName(), user.getPassword(),

	                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
 
	    }

	}
 
 
