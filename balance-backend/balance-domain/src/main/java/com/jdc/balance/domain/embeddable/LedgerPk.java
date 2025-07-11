package com.jdc.balance.domain.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class LedgerPk {

	private String code;
	@Column(name = "member_id")
	private long memberId;
}
