package com.bau.onlineappointmentapp.services;

import com.bau.onlineappointmentapp.Dtos.CreateUserDto;
import com.bau.onlineappointmentapp.models.User;
import com.bau.onlineappointmentapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(CreateUserDto createUserDto) {
        // Create User entity from DTO
        User user = new User();
        user.setName(createUserDto.getName());
        user.setSurname(createUserDto.getSurname());
        user.setEmail(createUserDto.getEmail());
        user.setPhoneNumber(createUserDto.getPhoneNumber());
        user.setGender(createUserDto.getGender());
        user.setDateOfBirth(createUserDto.getDateOfBirth());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        userRepository.save(user);
        return user;
    }
}