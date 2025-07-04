package com.jdc.balance.domain.embeddable;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SubscriptionPk {

	private LocalDate appliedAt;
	private int planId;
}
