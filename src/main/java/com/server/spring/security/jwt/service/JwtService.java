package com.server.spring.security.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token
     * @return the username contained in the token
     */
    String extractUserName(String token);

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails the user details
     * @return the generated JWT token
     */
    String generateToken(UserDetails userDetails);

    /**
     * Validates the given JWT token against the provided user details.
     *
     * @param token the JWT token
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
