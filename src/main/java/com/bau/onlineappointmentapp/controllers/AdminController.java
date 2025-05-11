package com.bau.onlineappointmentapp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import com.bau.onlineappointmentapp.services.AppointmentService;

import java.util.Locale;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public String adminDashboard(
            @RequestParam(value = "language", required = false) String lang,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
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
            model.addAttribute("appointments", appointmentService.getAllAppointments());
            
            // Debug info
            System.out.println("Current locale: " + currentLocale.getLanguage());
            System.out.println("Admin title text: " + messageSource.getMessage("admin.dashboard.title", null, currentLocale));
            
            return "admin_dashboard";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to load appointments. Please try again.");
            return "error";
        }
    }
    
    @PostMapping("/appointments/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/admin/dashboard";
    }
}
