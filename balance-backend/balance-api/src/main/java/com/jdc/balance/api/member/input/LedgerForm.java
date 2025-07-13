package com.jdc.balance.api.member.input;

import com.jdc.balance.domain.embeddable.LedgerPk;
import com.jdc.balance.domain.entity.Ledger;
import com.jdc.balance.domain.entity.Ledger.Type;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LedgerForm(
		@NotNull(message = "Please enter ledger type.")
		Type type,
		@NotEmpty(message = "Please enter ledger code.")
		String code,
		@NotEmpty(message = "Please enter ledger name.")
		String name,
		String description) {

	public Ledger entity(LedgerPk id) {
		var entity = new Ledger();
		entity.setId(id);
		entity.setName(name);
		entity.setType(type);
		entity.setDescription(description);
		return entity;
	}

}
