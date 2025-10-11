package com.server.spring.security.jwt.controller;

import com.server.spring.security.jwt.dto.JwtAuthenticationResponse;
import com.server.spring.security.jwt.dto.SignUpRequest;
import com.server.spring.security.jwt.dto.SigninRequest;
import com.server.spring.security.jwt.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Endpoint for user registration (signup).
     * Accepts a SignUpRequest object and returns a JWT authentication response.
     */
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    /**
     * Endpoint for user authentication (signin).
     * Accepts a SigninRequest object and returns a JWT authentication response.
     */
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
