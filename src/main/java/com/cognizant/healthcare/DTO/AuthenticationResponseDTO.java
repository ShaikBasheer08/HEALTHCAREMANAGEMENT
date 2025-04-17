package com.cognizant.healthcare.DTO;
//
//import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
@Data
public class AuthenticationResponseDTO {
 
//    @NotNull(message = "Token must not be null")
    private String token;
 
    public AuthenticationResponseDTO(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        this.token = token;
    }
 
    public String getToken() {
        return token;
    }
 
    public void setToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        this.token = token;
    }
}
 