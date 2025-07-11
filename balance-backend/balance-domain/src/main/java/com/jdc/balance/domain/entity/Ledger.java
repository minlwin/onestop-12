package com.jdc.balance.domain.entity;

import java.util.List;

import com.jdc.balance.domain.AuditableEntity;
import com.jdc.balance.domain.embeddable.LedgerPk;

import jakarta.persistence.Column;
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
public class Ledger extends AuditableEntity{

	@EmbeddedId
	private LedgerPk id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Type type;
	
	@Column(nullable = false)
	private String description;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "member_id", updatable = false, insertable = false)
	private Member member;
	
	@OneToMany(mappedBy = "ledger")
	private List<LedgerEntry> entries;
	
	public enum Type {
		Debit, Credit
	}
}
