package com.jdc.balance.api.anonymous;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.anonymous.input.SignUpForm;
import com.jdc.balance.api.anonymous.output.AuthResult;

@RestController
@RequestMapping("anonymous/signup")
public class MemberSignUpApi {

	@PostMapping
	AuthResult signUp(@RequestBody SignUpForm form) {
		return null;
	}
}
