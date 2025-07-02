package com.jdc.balance.api.anonymous.output;

import com.jdc.balance.domain.entity.Account.Role;

public record AuthResult(
		String email,
		String name,
		Role role,
		String accessToken,
		String refreshToken) {

}
