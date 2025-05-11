package com.bau.onlineappointmentapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent(message = "{appointment.date.futureOrPresent}")
    private LocalDate date;

    @NotNull(message = "{appointment.time.notnull}")
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to User
    private User user; // Bi-directional relationship with User

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Appointment(Long id, @FutureOrPresent(message = "{appointment.date.futureOrPresent}") LocalDate date,
			@NotNull(message = "{appointment.time.notnull}") LocalTime time, User user) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.user = user;
	}

	public Appointment() {
		super();
	}




}

