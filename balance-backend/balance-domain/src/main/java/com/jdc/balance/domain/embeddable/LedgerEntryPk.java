package com.jdc.balance.domain.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LedgerEntryPk {

	private String code;
	@Column(name = "member_id")
	private long memberId;
	@Column(name = "seq_number")
	private int seqNumber;
}
