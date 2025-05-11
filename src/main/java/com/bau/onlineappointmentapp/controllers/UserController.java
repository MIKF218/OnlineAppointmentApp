package com.bau.onlineappointmentapp.controllers;

import com.bau.onlineappointmentapp.Dtos.CreateUserDto;
import com.bau.onlineappointmentapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterPage(@RequestParam(value = "language", required = false) String lang,
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
            model.addAttribute("user", new CreateUserDto());
            // Debug info
            System.out.println("Current locale: " + currentLocale.getLanguage());
            return "register";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to load the registration page. Please try again.");
            return "error";
        }
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "language", required = false) String lang,
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

            // Debug info
            System.out.println("Current locale: " + currentLocale.getLanguage());

            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to load the login page. Please try again.");
            return "error";
        }
    }

    // Add a root mapping that redirects to the home page
    @GetMapping("/")
    public String index() {
        return "redirect:/welcomePage";
    }

   @GetMapping("/welcomePage")
   public String showWelcomeHomePage(@RequestParam(value = "language", required = false) String lang,
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
           model.addAttribute("welcomeMessage", "Welcome to the Online Appointment System!");

           // Debug info
           System.out.println("Current locale: " + currentLocale.getLanguage());

           return "welcome_page";
       } catch (Exception e) {
           e.printStackTrace();
           model.addAttribute("errorMessage", "Failed to load the welcome page. Please try again.");
           return "error";
       }
   }

    @PostMapping("/req/register")
    public String submitRegistration(@ModelAttribute("user") @Valid CreateUserDto user, 
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        try {
            userService.createUser(user);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/home")
    public String showHomePage(@RequestParam(value = "language", required = false) String lang,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            if (lang != null && !lang.isEmpty()) {
                Locale locale = new Locale(lang);
                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                if (localeResolver != null) {
                    localeResolver.setLocale(request, response, locale);
                }
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
                redirectAttributes.addFlashAttribute("authError", "Please sign in to view your appointments");
                return "redirect:/login";
            }

            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                return "redirect:/admin/dashboard";
            }

            Locale currentLocale = LocaleContextHolder.getLocale();
            model.addAttribute("currentLanguage", currentLocale.getLanguage());
            model.addAttribute("welcomeMessage", "Welcome to the Online Appointment System!");
            return "user_home";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred. Please try again.");
            return "redirect:/error";
        }
    }
}
