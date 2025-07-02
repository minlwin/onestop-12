package com.jdc.balance.api.anonymous;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.anonymous.input.SignInForm;
import com.jdc.balance.api.anonymous.input.TokenRefreshForm;
import com.jdc.balance.api.anonymous.output.AuthResult;
import com.jdc.balance.domain.entity.Account.Role;

@RestController
@RequestMapping("/anonymous/token")
public class TokenManagementApi {

	@PostMapping("generate")
	AuthResult generate(@Validated @RequestBody SignInForm form) {
		return new AuthResult(form.email(), "Admin User", Role.Admin, "Access Token", "Refresh Token");
	}

	@PostMapping("refresh")
	AuthResult refresh(@RequestBody TokenRefreshForm form) {
		return null;
	}
}
