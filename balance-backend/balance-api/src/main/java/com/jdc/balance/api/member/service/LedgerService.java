package com.jdc.balance.api.member.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.member.input.LedgerForm;
import com.jdc.balance.api.member.input.LedgerSearch;
import com.jdc.balance.api.member.output.LedgerListItem;
import com.jdc.balance.common.dto.ErrorMessage;
import com.jdc.balance.common.dto.ModificationResult;
import com.jdc.balance.common.exception.ApiBusinessException;
import com.jdc.balance.common.limit.LimitLedger;
import com.jdc.balance.domain.embeddable.LedgerPk;
import com.jdc.balance.domain.embeddable.LedgerPk_;
import com.jdc.balance.domain.entity.Ledger;
import com.jdc.balance.domain.entity.Ledger_;
import com.jdc.balance.domain.repo.LedgerRepo;
import com.jdc.balance.domain.repo.MemberRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerService {
	
	private final LedgerRepo ledgerRepo;
	private final MemberRepo memberRepo;
	
	@LimitLedger
	@Transactional
	public ModificationResult<LedgerPk> create(String username, LedgerForm form) {
		var ledger = ledgerRepo.create(form.entity(getId(username, form.code())));
		return ModificationResult.success(ledger.getId());
	}

	@Transactional
	public ModificationResult<LedgerPk> update(String username, LedgerForm form) {
		var pk = getId(username, form.code());
		var entity = safeCall(ledgerRepo.findById(pk), "Ledger", form.code());
		
		if(entity.getType() != form.type()) {
			throw new ApiBusinessException(ErrorMessage.withMessage("Invalid ledger type."));
		}
		
		entity.setName(form.name());
		entity.setDescription(form.description());
		
		return ModificationResult.success(entity.getId());
	}

	public List<LedgerListItem> search(String username, LedgerSearch search) {
		return ledgerRepo.search(cb -> {
			var cq = cb.createQuery(LedgerListItem.class);
			
			var root = cq.from(Ledger.class);
			LedgerListItem.select(cb, cq, root);
			
			cq.where(search.where(cb, root, username));
			cq.orderBy(cb.asc(root.get(Ledger_.id).get(LedgerPk_.code)));
			
			return cq;
		});
	}
	

	private LedgerPk getId(String username, String code) {
		var member = safeCall(memberRepo.findByAccountEmail(username), "Member", username);
		var pk = new LedgerPk();
		pk.setCode(code);
		pk.setMemberId(member.getId());
		return pk;
	}
}
