package com.app.logistics.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.Formula;

import com.app.logistics.entities.Role;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    //@Formula("CONCAT(first_name, '.', last_name))")
    private String username;

   // @Email(message = "Invalid email address")
   // @NotBlank(message = "Email is required")
    private String email;

	// @NotBlank(message = "Password is required")
	// @Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
	private Role role;
    private Boolean blocked;
}