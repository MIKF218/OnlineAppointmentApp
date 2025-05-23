package com.bau.onlineappointmentapp.repository;


import com.bau.onlineappointmentapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByEmail(String email);
}
