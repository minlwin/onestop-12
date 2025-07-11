package com.jdc.balance.domain.entity;

import java.time.LocalDate;
import java.util.List;

import com.jdc.balance.domain.AuditableEntity;
import com.jdc.balance.domain.embeddable.LedgerEntryPk;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	private LocalDate issueAt;
	
	@OneToMany(mappedBy = "entry")
	private List<LedgerEntryItem> items;
	
}
