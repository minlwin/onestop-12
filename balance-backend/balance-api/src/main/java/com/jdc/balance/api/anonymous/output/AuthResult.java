package com.jdc.balance.api.anonymous.output;

import com.jdc.balance.domain.entity.Account.Role;

public record AuthResult(
	    String email,
	    String name,
	    Role role,
	    String accessToken,
	    String refreshToken
	) {

	    public static class Builder {
	        private String email;
	        private String name;
	        private Role role;
	        private String accessToken;
	        private String refreshToken;

	        public Builder email(String email) {
	            this.email = email;
	            return this;
	        }

	        public Builder name(String name) {
	            this.name = name;
	            return this;
	        }

	        public Builder role(Role role) {
	            this.role = role;
	            return this;
	        }

	        public Builder accessToken(String accessToken) {
	            this.accessToken = accessToken;
	            return this;
	        }

	        public Builder refreshToken(String refreshToken) {
	            this.refreshToken = refreshToken;
	            return this;
	        }

	        public AuthResult build() {
	            return new AuthResult(email, name, role, accessToken, refreshToken);
	        }
	    }

	    public static Builder builder() {
	        return new Builder();
	    }
	}

