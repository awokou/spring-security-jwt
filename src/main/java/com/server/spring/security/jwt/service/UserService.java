package com.server.spring.security.jwt.service;

import com.server.spring.security.jwt.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDetailsService userDetailsService();
    List<UserDto> getAllUsers();
    Optional<UserDto> getUserById(Integer id);
    UserDto saveUser(UserDto userDto);
    UserDto updateUser(Integer id, UserDto userDto);
    void deleteUser(Integer id);
}
