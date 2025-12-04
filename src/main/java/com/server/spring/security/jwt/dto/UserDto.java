package com.server.spring.security.jwt.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
