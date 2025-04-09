package com.cognizant.healthcare.exception;

//Custom Exception Class
public class ResourceNotFoundException extends RuntimeException {

 public ResourceNotFoundException(String message) {
     super(message);
 }
}
