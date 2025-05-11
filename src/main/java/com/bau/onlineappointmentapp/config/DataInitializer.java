package com.bau.onlineappointmentapp.config;

import com.bau.onlineappointmentapp.models.User;
import com.bau.onlineappointmentapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.getByEmail("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setName("Administrator");
            adminUser.setSurname("Admin"); // Set surname
            adminUser.setEmail("admin@admin.com"); // Use a valid email format
            adminUser.setPassword(passwordEncoder.encode("1234"));
            adminUser.setPhoneNumber("0916066197"); // Ensure it matches the validation pattern
            adminUser.setGender("Male"); // Set gender
            adminUser.setRole("ADMIN");
            userRepository.save(adminUser);
            System.out.println("Admin user created: " + adminUser.getEmail());

        }
    }
}
