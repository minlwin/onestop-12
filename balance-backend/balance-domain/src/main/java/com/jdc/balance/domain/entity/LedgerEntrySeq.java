package com.jdc.balance.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class LedgerEntrySeq {

	@Id
	@Column(name = "seq_key")
	private String seqKey;
	
	@Column(name = "seq_number", nullable = false)
	private int seqNumber;
	
	public int nextSeq() {
		return ++ seqNumber;
	}
}
