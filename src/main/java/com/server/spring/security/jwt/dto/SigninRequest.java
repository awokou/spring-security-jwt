package com.server.spring.security.jwt.dto;

import lombok.*;

@Data
public class SigninRequest {
    private String email;
    private String password;
}
