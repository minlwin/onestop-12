package com.jdc.balance.api.member.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.member.input.LedgerEntryForm;
import com.jdc.balance.api.member.input.LedgerEntrySearch;
import com.jdc.balance.api.member.output.LedgerEntryDetails;
import com.jdc.balance.api.member.output.LedgerEntryListItem;
import com.jdc.balance.common.dto.ErrorMessage;
import com.jdc.balance.common.dto.ModificationResult;
import com.jdc.balance.common.exception.ApiBusinessException;
import com.jdc.balance.common.limit.LimitEntry;
import com.jdc.balance.common.utils.LedgerEntryItemMapper;
import com.jdc.balance.domain.PageResult;
import com.jdc.balance.domain.embeddable.LedgerEntryPk;
import com.jdc.balance.domain.embeddable.LedgerEntryPk_;
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
	private final LedgerEntryCutOffService cutOffDayService;
	
	@LimitEntry
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
		
		if(!cutOffDayService.canEdit(entry.getIssueAt())) {
			throw new ApiBusinessException(ErrorMessage.withMessage("Changes are not allowed as the cutoff date has passed."));
		}
		
		if(!entry.getId().getCode().equals(form.code())) {
			throw new IllegalArgumentException("Invalid ledger entry code: " + form.code());
		}
		
		entry.setItems(itemMapper.toJson(form.items()));
		entry.setParticular(form.particular());
		entry.setAmount(form.amount());
		
		// handle balance change
		var entries = getEntriesForUpdate(entry.getId().getMemberId(), entry.getCreatedAt());
		
		// Balance Calculation Function
		BiFunction<LedgerEntry, BigDecimal, BigDecimal> lastBalancFunc = (ledgerEntry, lastBalance) -> 
			switch(ledgerEntry.getLedger().getType()) {
			case Credit -> lastBalance.add(ledgerEntry.getAmount());
			case Debit -> lastBalance.subtract(ledgerEntry.getAmount());
			};
		
		// Calculate Balance
		var lastBalance = lastBalancFunc.apply(entry, entry.getLastBalance());
		
		for(var entity : entries) {
			// Calculate Last Balance for Each Entry
			entity.setLastBalance(lastBalance);
			lastBalance = lastBalancFunc.apply(entity, lastBalance);
		}
		
		// Update Member Balance
		var memberBalance = safeCall(memberBalanceRepo.findById(member.getId()), "Member Balance", member.getId());
		memberBalance.setBalance(lastBalance);
		
		return ModificationResult.success(entry.getId());
	}



	public LedgerEntryDetails findById(String username, String requestedId) {
		var member = safeCall(memberRepo.findByAccountEmail(username), "Member", username);	
		return safeCall(entryRepo.findById(getId(member.getId(), requestedId))
				.map(entry -> LedgerEntryDetails.from(
						entry, 
						cutOffDayService::canEdit,
						itemMapper::fromJson)), 
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
	
	private List<LedgerEntry> getEntriesForUpdate(long memberId, LocalDateTime createdAt) {
		return entryRepo.search(cb -> {
			var cq = cb.createQuery(LedgerEntry.class);
			var root = cq.from(LedgerEntry.class);
			cq.select(root);
			
			cq.where(
				cb.equal(root.get(LedgerEntry_.id).get(LedgerEntryPk_.memberId), memberId),
				cb.greaterThan(root.get(LedgerEntry_.createdAt), createdAt)
			);
			
			cq.orderBy(cb.asc(root.get(LedgerEntry_.createdAt)));
			
			return cq;
		});
	}

	
}
