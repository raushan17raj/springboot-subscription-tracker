package com.raushan.apps.subsentry.services;

import com.raushan.apps.subsentry.dto.LoginRequest;
import com.raushan.apps.subsentry.dto.SignupRequest;

public interface AuthService {

    /**
     * Authenticates a user and returns a JWT.
     * @param loginRequest DTO with email and password.
     * @return A JWT token as a String.
     */
    String login(LoginRequest loginRequest);

    /**
     * Registers a new user in the system.
     * @param signUpRequest DTO with email and password.
     * @return A success message.
     */
    String register(SignupRequest signUpRequest);
}