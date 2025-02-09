package com.server.spring.security.jwt.service.impl;

import com.server.spring.security.jwt.dto.UserDto;
import com.server.spring.security.jwt.entity.Role;
import com.server.spring.security.jwt.entity.User;
import com.server.spring.security.jwt.repository.UserRepository;
import com.server.spring.security.jwt.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@CacheConfig(cacheNames = { "user" })
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    @Cacheable
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public Optional<UserDto> getUserById(Integer id) {
        return userRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDto updateUser(Integer id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // Conversion methods between DTO and Entity
    private UserDto convertToDTO(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),user.getRole().name());
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.ADMIN);
        return user;
    }
}
