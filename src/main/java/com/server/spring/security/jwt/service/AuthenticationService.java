package com.server.spring.security.jwt.service;

import com.server.spring.security.jwt.dto.JwtAuthenticationResponse;
import com.server.spring.security.jwt.dto.SignUpRequest;
import com.server.spring.security.jwt.dto.SigninRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SigninRequest request);
}
