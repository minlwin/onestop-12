package com.jdc.balance.api.member.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.member.input.LedgerEntryForm;
import com.jdc.balance.api.member.input.LedgerEntrySearch;
import com.jdc.balance.api.member.output.LedgerEntryDetails;
import com.jdc.balance.api.member.output.LedgerEntryListItem;
import com.jdc.balance.common.dto.ModificationResult;
import com.jdc.balance.common.utils.LedgerEntryItemMapper;
import com.jdc.balance.domain.PageResult;
import com.jdc.balance.domain.embeddable.LedgerEntryPk;
import com.jdc.balance.domain.embeddable.LedgerPk;
import com.jdc.balance.domain.entity.Ledger.Type;
import com.jdc.balance.domain.entity.LedgerEntry;
import com.jdc.balance.domain.entity.LedgerEntry_;
import com.jdc.balance.domain.entity.MemberBalance;
import com.jdc.balance.domain.repo.LedgerEntryRepo;
import com.jdc.balance.domain.repo.LedgerRepo;
import com.jdc.balance.domain.repo.MemberBalanceRepo;
import com.jdc.balance.domain.repo.MemberRepo;
import com.jdc.balance.domain.service.LedgerEntryIdGenerator;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerEntryService {
	
	private final LedgerEntryRepo entryRepo;
	private final LedgerRepo ledgerRepo;
	private final MemberRepo memberRepo;
	private final MemberBalanceRepo memberBalanceRepo;

	private final LedgerEntryIdGenerator idGenerator;
	private final LedgerEntryItemMapper itemMapper;
	
	@Transactional
	public ModificationResult<LedgerEntryPk> create(String username, LedgerEntryForm form) {
		
		var member = safeCall(memberRepo.findByAccountEmail(username), "Member", username);	
		var ledger = safeCall(ledgerRepo.findById(new LedgerPk(form.code(), member.getId())), "Ledger", form.code());
		var memberBalance = memberBalanceRepo.findById(member.getId())
				.orElseGet(() -> {
					var entity = new MemberBalance();
					entity.setMemberId(member.getId());
					entity.setBalance(BigDecimal.ZERO);
					return memberBalanceRepo.create(entity);
				});
		
		var id = idGenerator.next(member.getId(), ledger.getId().getCode());
		
		var entry = new LedgerEntry();
		entry.setId(id);
		entry.setItems(itemMapper.toJson(form.items()));
		entry.setParticular(form.particular());
		entry.setIssueAt(LocalDate.now());
		entry.setAmount(form.amount());

		entry.setLastBalance(memberBalance.getBalance());
		if(ledger.getType() == Type.Credit) {
			memberBalance.setBalance(memberBalance.getBalance().add(form.amount()));
		} else {
			memberBalance.setBalance(memberBalance.getBalance().subtract(form.amount()));
		}
		
		entryRepo.create(entry);
		
		return ModificationResult.success(entry.getId());
	}


	@Transactional
	public ModificationResult<LedgerEntryPk> update(String username, String requestedId, LedgerEntryForm form) {
		var member = safeCall(memberRepo.findByAccountEmail(username), "Member", username);
		var entry = safeCall(entryRepo.findById(getId(member.getId(), requestedId)), "Ledger Entry", requestedId);
		
		if(!entry.getId().getCode().equals(form.code())) {
			throw new IllegalArgumentException("Invalid ledger entry code: " + form.code());
		}
		
		entry.setItems(itemMapper.toJson(form.items()));
		entry.setParticular(form.particular());
		entry.setAmount(form.amount());
		
		// TODO handle balance change
		
		return ModificationResult.success(entry.getId());
	}

	public LedgerEntryDetails findById(String username, String requestedId) {
		var member = safeCall(memberRepo.findByAccountEmail(username), "Member", username);	
		return safeCall(entryRepo.findById(getId(member.getId(), requestedId))
				.map(entry -> LedgerEntryDetails.from(entry, itemMapper::fromJson)), 
				"Ledger Entry", requestedId);
	}


	public PageResult<LedgerEntryListItem> search(String username, LedgerEntrySearch search, int page, int size) {
		return entryRepo.search(
				queryFunc(username, search), 
				countFunc(username, search),
				page, size);
	}

	private Function<CriteriaBuilder, CriteriaQuery<LedgerEntryListItem>> queryFunc(String username, LedgerEntrySearch search) {
		return cb -> {
			var cq = cb.createQuery(LedgerEntryListItem.class);
			var root = cq.from(LedgerEntry.class);
			LedgerEntryListItem.select(cq, root);
			cq.where(search.where(cb, root, username));
			cq.orderBy(cb.desc(root.get(LedgerEntry_.issueAt)));
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(String username, LedgerEntrySearch search) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(LedgerEntry.class);
			cq.select(cb.count(root));
			cq.where(search.where(cb, root, username));
			return cq;
		};
	}

	private LedgerEntryPk getId(long memberId, String code) {
		
		var pk = LedgerEntryPk.from(memberId, code);
		
		if(pk == null) {
			throw new IllegalArgumentException("Invalid ledger entry code: " + code);
		}
		
		return pk;
	}
	
}
