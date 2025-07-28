package com.jdc.balance.api.management.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.common.dto.SubscriptionStatusSummary;
import com.jdc.balance.common.dto.YearMonthData;
import com.jdc.balance.domain.embeddable.SubscriptionPk_;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.entity.SubscriptionPlan_;
import com.jdc.balance.domain.entity.Subscription_;
import com.jdc.balance.domain.repo.SubscriptionRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardService {

	private final SubscriptionRepo subscriptionRepo;

	public List<Integer> getYears() {
		
		var startYear = subscriptionRepo.findFirstByOrderByCreatedAt()
			.map(a -> a.getCreatedAt().getYear())
			.orElse(LocalDate.now().getYear());
		
		var currentYear = LocalDate.now().getYear();
		
		if(startYear == currentYear) {
			return List.of(startYear);
		}

		return IntStream.rangeClosed(startYear, currentYear)
				.boxed()
				.toList();
	}

	public Map<Status, Map<String, Long>> getSummary(YearMonthData data) {
		
		var list = subscriptionRepo.search(cb -> {
			var cq = cb.createQuery(SubscriptionStatusSummary.class);
			var root = cq.from(Subscription.class);
			
			var startDate = data.getStartDate();
			var endDate = data.getEndDate();
			
			cq.multiselect(
				root.get(Subscription_.status),
				root.get(Subscription_.plan).get(SubscriptionPlan_.name),
				cb.count(root.get(Subscription_.id).get(SubscriptionPk_.planId))
			);
			
			cq.groupBy(
				root.get(Subscription_.status),
				root.get(Subscription_.plan).get(SubscriptionPlan_.name)
			);
			
			cq.where(
				cb.greaterThanOrEqualTo(root.get(Subscription_.id).get(SubscriptionPk_.appliedAt), startDate),
				cb.lessThan(root.get(Subscription_.id).get(SubscriptionPk_.appliedAt), endDate)
			);
			
			return cq;
		});
		
		return list.stream()
				.collect(Collectors.groupingBy(SubscriptionStatusSummary::status,
						Collectors.groupingBy(SubscriptionStatusSummary::planName,
								Collectors.reducing(0L, SubscriptionStatusSummary::count, Long::sum))));
	}
	
	public Map<LocalDate, Long> getProgress(YearMonthData data) {
		
		var result = new LinkedHashMap<LocalDate, Long>();
		
		var start = data.getStartDate();
		var end = data.getEndDate();
		
		while(start.isBefore(end)) {
			var next = data.next(start);
			result.put(start, getCount(start, next));
			start = next;
		}
		
		return result;
	}

	private Long getCount(LocalDate start, LocalDate next) {
		return subscriptionRepo.searchOne(cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Subscription.class);
			
			cq.select(
				cb.count(root.get(Subscription_.id).get(SubscriptionPk_.planId))
			);

			cq.where(
				cb.greaterThanOrEqualTo(root.get(Subscription_.id).get(SubscriptionPk_.appliedAt), start),
				cb.lessThan(root.get(Subscription_.id).get(SubscriptionPk_.appliedAt), next)
			);

			return cq;
		});
	}	

}
