package com.cognizant.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.healthcare.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
