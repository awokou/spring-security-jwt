package com.server.spring.security.jwt.service;

import com.server.spring.security.jwt.dto.reponse.JwtAuthenticationResponse;
import com.server.spring.security.jwt.dto.request.SignUpRequest;
import com.server.spring.security.jwt.dto.request.SigninRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SigninRequest request);
}
