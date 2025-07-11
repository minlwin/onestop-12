package com.jdc.balance.domain.entity;

import java.math.BigDecimal;

import com.jdc.balance.domain.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class MemberBalance extends AuditableEntity{

	@Id
	private long memberId;
	@Column(nullable = false)
	private BigDecimal balance;
}
