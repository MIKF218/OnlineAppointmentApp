package com.bau.onlineappointmentapp.repository;

import com.bau.onlineappointmentapp.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> getByUserId(Long userId);
    List<Appointment> getByUserEmail(String userEmail);
    List<Appointment> getByDate(LocalDate date);
    List<Appointment> getByDateAndTime(LocalDate date, LocalTime time);
}
