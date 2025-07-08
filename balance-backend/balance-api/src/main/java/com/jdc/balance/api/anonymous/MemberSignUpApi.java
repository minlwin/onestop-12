package com.jdc.balance.api.anonymous;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.anonymous.input.SignUpForm;
import com.jdc.balance.api.anonymous.output.AuthResult;
import com.jdc.balance.api.anonymous.service.SignUpService;
import com.jdc.balance.common.security.token.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("anonymous/signup")
public class MemberSignUpApi {
	
	private final SignUpService service;
	private final JwtTokenProvider tokenProvider;

	@PostMapping
	AuthResult signUp(@Validated @RequestBody SignUpForm form) {
		
		var member = service.signUp(form);
		var authentication = UsernamePasswordAuthenticationToken.authenticated(
				form.email(), null, 
				List.of(new SimpleGrantedAuthority(member.getAccount().getRole().name())));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return AuthResult.builder()
				.email(form.email())
				.role(member.getAccount().getRole())
				.name(member.getAccount().getName())
				.expired(member.getAccount().isExpired())
				.accessToken(tokenProvider.generateAccessToken(authentication))
				.refreshToken(tokenProvider.generateRefreshToken(authentication))
				.build();
	}
}
