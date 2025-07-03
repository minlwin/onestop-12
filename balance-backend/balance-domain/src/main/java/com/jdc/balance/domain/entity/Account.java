package com.jdc.balance.domain.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Account {
	
	@Id
	private String email;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private Role role;
	
	private LocalDate expiredAt;
	
	public enum Role {
		Admin, Member
	}
}
