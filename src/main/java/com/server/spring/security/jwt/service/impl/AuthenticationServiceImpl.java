package com.server.spring.security.jwt.service.impl;

import com.server.spring.security.jwt.dto.JwtAuthenticationResponse;
import com.server.spring.security.jwt.dto.SignUpRequest;
import com.server.spring.security.jwt.dto.SigninRequest;
import com.server.spring.security.jwt.entity.enums.Role;
import com.server.spring.security.jwt.entity.User;
import com.server.spring.security.jwt.exception.UserAlreadyExistsException;
import com.server.spring.security.jwt.repository.UserRepository;
import com.server.spring.security.jwt.service.AuthenticationService;
import com.server.spring.security.jwt.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * User registration (signup) and authentication (signin) methods.
     * Signup creates a new user, encodes the password, assigns a default role,
     * saves the user to the repository, and generates a JWT token.
     * Signin authenticates the user credentials and generates a JWT token upon success.
     */
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {

        // Vérifier si l'utilisateur existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("L'utilisateur avec cet email existe déjà");
        }

        // Créer un nouvel utilisateur
        User  user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        // Sauvegarder l'utilisateur dans la base
        userRepository.save(user);

        // Générer un token JWT
        var jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    /**
     * Authenticate user and generate JWT token.
     * Throws IllegalArgumentException if authentication fails.
     */
    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
}
