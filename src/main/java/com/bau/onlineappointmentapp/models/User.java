package com.bau.onlineappointmentapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Users") // Escaping the reserved keyword
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{user.name.notblank}")
    @Pattern(regexp = "^[A-Za-z]+$", message = "{user.name.invalid}")
    @Size(min = 3, message = "{user.name.size}")
    private String name;

    @NotBlank(message = "{user.surname.notblank}")
    @Pattern(regexp = "^[A-Za-z]+$", message = "{user.surname.invalid}")
    @Size(min = 3, message = "{user.surname.size}")
    private String surname;

    @Past(message = "{user.dob.past}")  // Changed from user.dateOfBirth.past to user.dob.past
    private LocalDate dateOfBirth;

    @NotBlank(message = "{user.phone.notblank}")
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "{user.phone.valid}")  // Changed from user.phone.invalid to user.phone.valid
    private String phoneNumber;

    @NotNull(message = "{user.gender.notnull}")  // Changed from user.gender.notblank to user.gender.notnull
    private String gender;

    @Email(message = "{user.email.valid}")
    @NotBlank(message = "{user.email.notblank}")
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    @Size(min = 8, message = "{user.password.size}")
    private String password;

    private String role = "USER" ;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public User(Long id,
			@NotBlank(message = "{user.name.notblank}") @Pattern(regexp = "^[A-Za-z]+$", message = "{user.name.invalid}") @Size(min = 3, message = "{user.name.size}") String name,
			@NotBlank(message = "{user.surname.notblank}") @Pattern(regexp = "^[A-Za-z]+$", message = "{user.surname.invalid}") @Size(min = 3, message = "{user.surname.size}") String surname,
			@Past(message = "{user.dob.past}") LocalDate dateOfBirth,
			@NotBlank(message = "{user.phone.notblank}") @Pattern(regexp = "\\+?[0-9]{10,15}", message = "{user.phone.valid}") String phoneNumber,
			@NotNull(message = "{user.gender.notnull}") String gender,
			@Email(message = "{user.email.valid}") @NotBlank(message = "{user.email.notblank}") String email,
			@NotBlank(message = "{user.password.notblank}") @Size(min = 8, message = "{user.password.size}") String password,
			String role, List<Appointment> appointments) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.role = role;
		this.appointments = appointments;
	}

	public User() {
		super();
	}
    
    
}
