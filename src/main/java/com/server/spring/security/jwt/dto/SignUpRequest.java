package com.server.spring.security.jwt.dto;

import lombok.*;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
