package com.server.spring.security.jwt.dto;

import lombok.*;

@Data
@Builder
public class JwtAuthenticationResponse {
    private String token;
}
