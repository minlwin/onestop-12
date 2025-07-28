package com.jdc.balance.api.member.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.common.dto.LedgerEntrySummary;
import com.jdc.balance.common.dto.YearMonthData;
import com.jdc.balance.domain.embeddable.LedgerEntryPk_;
import com.jdc.balance.domain.entity.Ledger.Type;
import com.jdc.balance.domain.entity.LedgerEntry;
import com.jdc.balance.domain.entity.LedgerEntry_;
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
				root.get(LedgerEntry_.ledger),
				cb.sum(root.get(LedgerEntry_.amount))
			);
			
			cq.groupBy(root.get(LedgerEntry_.ledger));
			
			cq.where(
				cb.equal(root.get(LedgerEntry_.id).get(LedgerEntryPk_.memberId), member.getId()),
				cb.greaterThanOrEqualTo(root.get(LedgerEntry_.issueAt), data.getStartDate()),
				cb.lessThan(root.get(LedgerEntry_.issueAt), data.getEndDate())
			);
			
			return cq;
		});
		
		for(var summary : list) {
			
		}

		return result;
	}

	public Map<Type, Map<LocalDate, BigDecimal>> getProgress(String username, YearMonthData data) {

		var member = safeCall(memberRepo.findByAccountEmail(username), "member", username);
		return null;
	}

}
