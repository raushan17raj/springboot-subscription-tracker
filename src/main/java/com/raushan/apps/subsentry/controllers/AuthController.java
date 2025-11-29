package com.raushan.apps.subsentry.controllers;

import com.raushan.apps.subsentry.dto.JwtAuthResponse;
import com.raushan.apps.subsentry.dto.LoginRequest;
import com.raushan.apps.subsentry.dto.SignupRequest;
import com.raushan.apps.subsentry.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Handles user login requests.
     * Authenticates the user and returns a JWT token.
     *
     * @param loginRequest DTO containing email and password.
     * @return ResponseEntity with JwtAuthResponse containing the token.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        // 1. Call the AuthService to authenticate and get the JWT token
        String token = authService.login(loginRequest);

        // 2. Create the response DTO
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token);

        // 3. Return the response DTO with a 200 OK status
        return ResponseEntity.ok(jwtAuthResponse);
    }

    /**
     * Handles new user registration requests.
     *
     * @param signUpRequest DTO containing user details.
     * @return ResponseEntity with a success message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signUpRequest){
        System.out.println("Received registration request for: " + signUpRequest.getEmail()); // Basic logging

        // Call the service to register the user (handles hashing etc.)
        String message = authService.register(signUpRequest);

        System.out.println("Registration result: " + message); // Basic logging

        // Return success message with a 201 CREATED status
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", message));
    }
}