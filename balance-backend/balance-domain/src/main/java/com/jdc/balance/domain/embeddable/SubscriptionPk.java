package com.jdc.balance.domain.embeddable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SubscriptionPk {
	
	private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyyMMdd");

	@Column(name = "applied_at")
	private LocalDate appliedAt;
	@Column(name = "plan_id")
	private int planId;
	@Column(name = "member_id")
	private long memberId;
	
	public static SubscriptionPk from(String code) {
		
		var dateStr = code.substring(0, 8);
		var planIdStr = code.substring(8, 10);
		var memberIdStr = code.substring(10);
		
		var id = new SubscriptionPk();
		
		id.setAppliedAt(LocalDate.parse(dateStr, DF));
		id.setPlanId(Integer.parseInt(planIdStr));
		id.setMemberId(Long.parseLong(memberIdStr));
		
		return id;
	}
	
	public String getCode() {
		return "%s%02d%06d".formatted(appliedAt.format(DF), planId, memberId);
	}
}
