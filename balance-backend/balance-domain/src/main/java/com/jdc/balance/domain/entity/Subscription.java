package com.jdc.balance.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jdc.balance.domain.AuditableEntity;
import com.jdc.balance.domain.embeddable.SubscriptionPk;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Subscription extends AuditableEntity{

	@EmbeddedId
	private SubscriptionPk id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "plan_id", referencedColumnName = "id", insertable = false, updatable = false)
	private SubscriptionPlan plan;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "member_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Member member;
	
	private LocalDate startAt;
	private Status status;
	private String reason;
	private LocalDateTime statusChangeAt;
	
	
	@ManyToOne
	private PaymentMethod payment;
	private String paymentSlip;
	private int paymentAmount;
	
	public enum Status {
		Pending, Approved, Denied
	}
}
