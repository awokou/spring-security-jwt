package com.server.spring.security.jwt.service;

import com.server.spring.security.jwt.dto.JwtAuthenticationResponse;
import com.server.spring.security.jwt.dto.SignUpRequest;
import com.server.spring.security.jwt.dto.SigninRequest;

public interface AuthenticationService {

    /**
     * Signs up a new user and returns a JWT authentication response.
     *
     * @param request the sign-up request containing user details
     * @return a JWT authentication response with token and user info
     */
    JwtAuthenticationResponse signup(SignUpRequest request);

    /**
     * Signs in an existing user and returns a JWT authentication response.
     *
     * @param request the sign-in request containing user credentials
     * @return a JWT authentication response with token and user info
     */
    JwtAuthenticationResponse signin(SigninRequest request);
}
