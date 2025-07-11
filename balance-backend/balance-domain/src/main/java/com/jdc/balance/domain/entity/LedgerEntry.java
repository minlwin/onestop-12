package com.jdc.balance.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.jdc.balance.domain.AuditableEntity;
import com.jdc.balance.domain.embeddable.LedgerEntryPk;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class LedgerEntry extends AuditableEntity{

	@EmbeddedId
	private LedgerEntryPk id;
	
	@ManyToOne
	@JoinColumn(name = "code", insertable = false, updatable = false)
	@JoinColumn(name = "member_id", insertable = false, updatable = false)
	private Ledger ledger;
	
	@ManyToOne
	@JoinColumn(name = "member_id", insertable = false, updatable = false)
	private Member member;
	
	@Column(nullable = false)
	private LocalDate issueAt;

	@Column(nullable = false)
	private String particular;
	
	@Column(nullable = false)
	private BigDecimal lastBalance;

	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(columnDefinition = "TEXT")
	private String items;
	
}
