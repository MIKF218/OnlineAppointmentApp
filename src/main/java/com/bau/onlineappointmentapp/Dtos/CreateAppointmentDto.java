package com.bau.onlineappointmentapp.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;



@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentDto {

    @NotNull
    LocalDate date;

    @NotNull
    LocalTime time;

    @NotNull
    @Email
    String userEmail;

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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public CreateAppointmentDto(@NotNull LocalDate date, @NotNull LocalTime time, @NotNull @Email String userEmail) {
		super();
		this.date = date;
		this.time = time;
		this.userEmail = userEmail;
	}

	public CreateAppointmentDto() {
		super();
	}
    
    
}
