package com.jdc.balance.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class LedgerEntryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(optional = false)
	private LedgerEntry entry;
	
	@Column(nullable = false)
	private String item;
	
	@Column(nullable = false)
	private BigDecimal unitPrice;

	@Column(nullable = false)
	private int quantity;
	
	private String remark;
}
