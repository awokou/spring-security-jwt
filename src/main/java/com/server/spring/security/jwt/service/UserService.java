package com.server.spring.security.jwt.service;

import com.server.spring.security.jwt.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Provides the UserDetailsService for authentication purposes.
     *
     * @return an instance of UserDetailsService
     */
    UserDetailsService userDetailsService();

    /**
     * Retrieves all users.
     *
     * @return a list of UserDto objects
     */
    List<UserDto> getAllUsers();

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return an Optional containing the UserDto if found, or empty if not found
     */
    Optional<UserDto> getUserById(Integer id);

    /**
     * Saves a new user.
     *
     * @param userDto the UserDto object to be saved
     * @return the saved UserDto object
     */
    UserDto saveUser(UserDto userDto);

    /**
     * Updates an existing user.
     *
     * @param id      the ID of the user to be updated
     * @param userDto the UserDto object containing updated information
     * @return the updated UserDto object
     */
    UserDto updateUser(Integer id, UserDto userDto);

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted
     */
    void deleteUser(Integer id);
}
