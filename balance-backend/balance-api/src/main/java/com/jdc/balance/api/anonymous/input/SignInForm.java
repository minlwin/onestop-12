package com.jdc.balance.api.anonymous.input;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import jakarta.validation.constraints.NotBlank;

public record SignInForm(
		@NotBlank(message = "Please enter email for login.")
		String email,
		@NotBlank(message = "Please enter password.")
		String password) {

	public Authentication getAuthentication() {
		return UsernamePasswordAuthenticationToken.unauthenticated(email, password);
	}

}
