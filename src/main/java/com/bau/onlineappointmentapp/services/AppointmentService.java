package com.bau.onlineappointmentapp.services;

import com.bau.onlineappointmentapp.Dtos.CreateAppointmentDto;
import com.bau.onlineappointmentapp.config.SecurityConfig;
import com.bau.onlineappointmentapp.models.Appointment;
import com.bau.onlineappointmentapp.models.User;
import com.bau.onlineappointmentapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bau.onlineappointmentapp.repository.AppointmentRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;

    public Appointment createAppointment(CreateAppointmentDto createAppointmentDto)
    {
       User user = userRepository.getByEmail(createAppointmentDto.getUserEmail())
               .orElseThrow(() -> new RuntimeException("User not found"));
       
       // Check if the time slot is already booked
       List<Appointment> existingAppointments = appointmentRepository.getByDateAndTime(
           createAppointmentDto.getDate(), createAppointmentDto.getTime());
       
       if (!existingAppointments.isEmpty()) {
           throw new RuntimeException("This time slot is already booked");
       }
       
       Appointment appointment = new Appointment();
       appointment.setDate(createAppointmentDto.getDate());
       appointment.setTime(createAppointmentDto.getTime());
       appointment.setUser(user);
       return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByEmail(String email) {
        return appointmentRepository.getByUserEmail(email);
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<LocalTime> getAllTimeSlots(LocalDate date) {
        // Get all appointments for the selected date
        List<Appointment> appointments = appointmentRepository.getByDate(date);
        
        // Extract all booked times for that date
        List<LocalTime> bookedTimes = appointments.stream()
                .map(Appointment::getTime)
                .collect(Collectors.toList());

        // Generate all possible time slots (30-minute intervals from 9 AM to 5 PM)
        List<LocalTime> allTimes = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        
        while (startTime.isBefore(endTime)) {
            // Only add the time if it's not already booked
            if (!bookedTimes.contains(startTime)) {
                allTimes.add(startTime);
            }
            startTime = startTime.plusMinutes(30);
        }

        return allTimes;
    }
    
    // New method to return all time slots with their availability status
    public Map<LocalTime, Boolean> getAllTimeSlotsWithStatus(LocalDate date) {
        // Get all appointments for the selected date
        List<Appointment> appointments = appointmentRepository.getByDate(date);
        
        // Extract all booked times for that date
        Set<LocalTime> bookedTimes = appointments.stream()
                .map(Appointment::getTime)
                .collect(Collectors.toSet());

        // Generate all possible time slots (30-minute intervals from 9 AM to 5 PM)
        Map<LocalTime, Boolean> allTimeSlotsWithStatus = new LinkedHashMap<>();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        
        while (startTime.isBefore(endTime)) {
            // Map key is the time slot, value is whether it's available (true) or booked (false)
            allTimeSlotsWithStatus.put(startTime, !bookedTimes.contains(startTime));
            startTime = startTime.plusMinutes(30);
        }

        return allTimeSlotsWithStatus;
    }

}
