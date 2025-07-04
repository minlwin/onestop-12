package com.jdc.balance.domain.entity;

import com.jdc.balance.domain.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class SubscriptionPlan extends AuditableEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	private int months;
	private int fees;
	private Integer maxLedgers;
	private Integer dailyEntry;
	private Integer monthlyEntry;
	private boolean defaultPaln;
	private boolean active;
}
