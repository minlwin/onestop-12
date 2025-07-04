package com.jdc.balance.domain.embeddable;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SubscriptionPk {

	@Column(name = "applied_at")
	private LocalDate appliedAt;
	@Column(name = "plan_id")
	private int planId;
}
