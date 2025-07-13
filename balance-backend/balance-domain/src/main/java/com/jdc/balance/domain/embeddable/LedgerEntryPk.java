package com.jdc.balance.domain.embeddable;

import org.springframework.util.StringUtils;

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
	
	public String getRequestId() {
		return "%s-%04d".formatted(code, seqNumber);
	}
	
	public static LedgerEntryPk from(long memberId, String requestedId) {
		if(StringUtils.hasLength(requestedId)) {
			var parts = requestedId.split("-");
			if(parts.length >= 2) {
				var seqNumber = Integer.parseInt(parts[parts.length - 1]);
				var code = requestedId.substring(0, requestedId.length() - 5);
				return new LedgerEntryPk(code, memberId, seqNumber);
			}
		}
		
		return null;
	}
}
