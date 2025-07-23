package com.jdc.balance.domain.entity;

import java.time.LocalDate;

import com.jdc.balance.domain.AuditableEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Member extends AuditableEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Account account;
	private LocalDate enabledDate;
	
	private String phone;
	private String profileImage;
	private String address;
	
	@ManyToOne
	private Subscription subscription;
	
	@ManyToOne
	@JoinColumn(name = "subscription_plan_id", referencedColumnName = "id", insertable = false, updatable = false)
	private SubscriptionPlan plan;
}
