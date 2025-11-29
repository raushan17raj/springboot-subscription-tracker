package com.raushan.apps.subsentry.services;

import com.raushan.apps.subsentry.dto.LoginRequest;
import com.raushan.apps.subsentry.dto.SignupRequest;
import com.raushan.apps.subsentry.entities.Role;
import com.raushan.apps.subsentry.entities.User;
import com.raushan.apps.subsentry.exception.ApiException;
import com.raushan.apps.subsentry.repositories.RoleRepository;
import com.raushan.apps.subsentry.repositories.UserRepository;
import com.raushan.apps.subsentry.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 1. Authenticates the user with AuthenticationManager.
     * 2. Generates and returns a JWT token.
     */
    @Override
    public String login(LoginRequest loginRequest) {
        // This line does the actual authentication (checks email and password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // If successful, set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate and return the JWT token
        return jwtTokenProvider.generateToken(authentication);
    }

    /**
     * 1. Hashes the password with PasswordEncoder.
     * 2. Saves the new user.
     */
    @Override
    public String register(SignupRequest signUpRequest) {
        // Check if email already exists
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ApiException("Email is already in use!");
        }

        // Create a new User object
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFullName(signUpRequest.getFullName());
        user.setMobileNumber(signUpRequest.getMobileNumber());

        // Hash the password before saving it to the database
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        // --- ASSIGN DEFAULT ROLE ---
        // Find the USER role (assuming it exists in the DB)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ApiException("Error: Role 'ROLE_USER' not found."));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles); // Set the roles for the new user
        // --- END OF ROLE ASSIGNMENT ---


        User newUser = userRepository.save(user);
        return "New User registered with ID: " + newUser.getId();
    }
}