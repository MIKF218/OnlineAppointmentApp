package com.bau.onlineappointmentapp.controllers;

import com.bau.onlineappointmentapp.Dtos.CreateAppointmentDto;
import com.bau.onlineappointmentapp.models.Appointment;
import com.bau.onlineappointmentapp.services.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/book")
    @PreAuthorize("isAuthenticated()")
    public String showBookingForm(@RequestParam(value = "language", required = false) String lang,
                                  HttpServletRequest request,
                                  HttpServletResponse response, Model model) {
        try {
            if (lang != null && !lang.isEmpty()) {
                Locale locale = new Locale(lang);
                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                if (localeResolver != null) {
                    localeResolver.setLocale(request, response, locale);
                }
            }
            // Add the current language to the model for display
            Locale currentLocale = LocaleContextHolder.getLocale();
            model.addAttribute("currentLanguage", currentLocale.getLanguage());
            model.addAttribute("appointment", new CreateAppointmentDto());

            // Debug info
            System.out.println("Current locale: " + currentLocale.getLanguage());
            System.out.println("Booking form title: " + messageSource.getMessage("register.button", null, currentLocale));

            return "book_appointment";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to load booking form. Please try again.");
            return "error";
        }
    }

    @PostMapping("/book")
    @PreAuthorize("isAuthenticated()")
    public String bookAppointment(@ModelAttribute CreateAppointmentDto appointmentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        appointmentDto.setUserEmail(authentication.getName());
        appointmentService.createAppointment(appointmentDto);
        return "redirect:/appointments/my";
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public String viewMyAppointments(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Appointment> appointments = appointmentService.getAppointmentsByEmail(authentication.getName());
        model.addAttribute("appointments", appointments);
        return "my_appointments";
    }

    @GetMapping("/available-slots")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public List<LocalTime> getAvailableSlots(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return appointmentService.getAllTimeSlots(parsedDate);
    }

    @GetMapping("/all-slots-with-status")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public Map<String, Boolean> getAllSlotsWithStatus(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        Map<LocalTime, Boolean> slotsWithStatus = appointmentService.getAllTimeSlotsWithStatus(parsedDate);
        
        // Convert LocalTime keys to strings for JSON serialization
        Map<String, Boolean> response = new LinkedHashMap<>();
        slotsWithStatus.forEach((time, isAvailable) -> 
            response.put(time.toString(), isAvailable));
            
        return response;
    }
    @PostMapping("/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id, Model model) {
        try {
            appointmentService.deleteAppointment(id);
            return "redirect:/appointments/my";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to cancel the appointment. Please try again.");
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments/book";
    }
}
