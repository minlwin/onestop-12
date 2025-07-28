package com.jdc.balance.api.member.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.member.output.MemberProgress;
import com.jdc.balance.api.member.output.MemberProgressSelect;
import com.jdc.balance.common.dto.LedgerEntrySummary;
import com.jdc.balance.common.dto.YearMonthData;
import com.jdc.balance.domain.embeddable.LedgerEntryPk_;
import com.jdc.balance.domain.embeddable.LedgerPk_;
import com.jdc.balance.domain.entity.Ledger.Type;
import com.jdc.balance.domain.entity.LedgerEntry;
import com.jdc.balance.domain.entity.LedgerEntry_;
import com.jdc.balance.domain.entity.Ledger_;
import com.jdc.balance.domain.repo.LedgerEntryRepo;
import com.jdc.balance.domain.repo.MemberRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service("memberDashboardService")
public class DashboardService {
	
	private final LedgerEntryRepo entryRepo;
	private final MemberRepo memberRepo;
	
	public List<Integer> getYears(String username) {
		
		var member = safeCall(memberRepo.findByAccountEmail(username), "member", username);

		var startYear = entryRepo.findStartEntry(member.getId())
				.map(a -> a.getYear())
				.orElse(LocalDate.now().getYear());

		var currentYear = LocalDate.now().getYear();
		
		if(startYear == currentYear) {
			return List.of(startYear);
		}

		return IntStream.rangeClosed(startYear, currentYear)
				.boxed()
				.toList();
	}

	public Map<String, Map<String, BigDecimal>> getSummary(String username, YearMonthData data) {
		
		var member = safeCall(memberRepo.findByAccountEmail(username), "member", username);
		var result = new LinkedHashMap<String, Map<String, BigDecimal>>();
		
		var list = entryRepo.search(cb -> {
			var cq = cb.createQuery(LedgerEntrySummary.class);
			var root = cq.from(LedgerEntry.class);
			
			cq.multiselect(
				root.get(LedgerEntry_.ledger).get(Ledger_.id).get(LedgerPk_.memberId),
				root.get(LedgerEntry_.ledger).get(Ledger_.id).get(LedgerPk_.code),
				root.get(LedgerEntry_.ledger).get(Ledger_.type),
				root.get(LedgerEntry_.ledger).get(Ledger_.name),
				cb.sum(root.get(LedgerEntry_.amount))
			);
			
			cq.groupBy(
				root.get(LedgerEntry_.ledger).get(Ledger_.id).get(LedgerPk_.memberId),
				root.get(LedgerEntry_.ledger).get(Ledger_.id).get(LedgerPk_.code),
				root.get(LedgerEntry_.ledger).get(Ledger_.type),
				root.get(LedgerEntry_.ledger).get(Ledger_.name)
			);
			
			cq.where(
				cb.equal(root.get(LedgerEntry_.id).get(LedgerEntryPk_.memberId), member.getId()),
				cb.greaterThanOrEqualTo(root.get(LedgerEntry_.issueAt), data.getStartDate()),
				cb.lessThan(root.get(LedgerEntry_.issueAt), data.getEndDate())
			);
			
			return cq;
		});
		
		var map = list.stream().collect(Collectors.groupingBy(
				LedgerEntrySummary::type, 
				Collectors.groupingBy(
						LedgerEntrySummary::ledger,
						Collectors.reducing(BigDecimal.ZERO, 
								LedgerEntrySummary::total, 
								BigDecimal::add)
				)
			));
		
		for(var key : map.keySet()) {
			var typeMap = map.get(key);
			if(typeMap != null && !typeMap.isEmpty()) {
				result.put(key.name(), typeMap);
			}
		}
		
		var summary = new LinkedHashMap<String, BigDecimal>();
		summary.put(Type.Debit.name(), map.getOrDefault(Type.Debit, Map.of()).values().stream()
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		summary.put(Type.Credit.name(), map.getOrDefault(Type.Credit, Map.of()).values().stream()
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		
		result.put("Summary", summary);

		return result;
	}

	public List<MemberProgress> getProgress(String username, YearMonthData data) {

		var member = safeCall(memberRepo.findByAccountEmail(username), "member", username);
		var result = new ArrayList<MemberProgress>();
		
		var start = data.getStartDate();
		var end = data.getEndDate();
		
		while(start.isBefore(end)) {
			var next = data.next(start);
			var progress = getMemberProgress(member.getId(), start, next);
			result.add(progress);
			start = next;
		}
		
		return result;
	}
	
	public MemberProgress getMemberProgress(long memberId, LocalDate start, LocalDate next) {
		
		var optional = entryRepo.searchOne(cb -> {
			var cq = cb.createQuery(MemberProgressSelect.class);
			var root = cq.from(LedgerEntry.class);
			
			MemberProgressSelect.select(cb, cq, root);
			
			cq.where(
				cb.equal(root.get(LedgerEntry_.id).get(LedgerEntryPk_.memberId), memberId),
				cb.greaterThanOrEqualTo(root.get(LedgerEntry_.issueAt), start),
				cb.lessThan(root.get(LedgerEntry_.issueAt), next)
			);
			
			return cq;
		});
		
		return optional.map(a -> MemberProgress.of(start, a))
			.orElse(new MemberProgress(start, BigDecimal.ZERO, BigDecimal.ZERO));
	}

}
