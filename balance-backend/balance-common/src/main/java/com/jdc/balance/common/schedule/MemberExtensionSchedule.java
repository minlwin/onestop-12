package com.jdc.balance.common.schedule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.domain.entity.Account_;
import com.jdc.balance.domain.entity.Member_;
import com.jdc.balance.domain.entity.Subscription;
import com.jdc.balance.domain.entity.Subscription.Status;
import com.jdc.balance.domain.entity.Subscription_;
import com.jdc.balance.domain.repo.SubscriptionRepo;

@Service
public class MemberExtensionSchedule {
	
	@Autowired
	private SubscriptionRepo repo;

	@Transactional
	@Scheduled(cron = "0 1 0 * * ?") // Every day at midnight
	public void extendApprovedMembers() {
		// Find Approved Subscriptions
		var subscriptions = findApprovedSubscriptions();
		
		// Update Plan for Members
		for(var subscription : subscriptions) {
			var member = subscription.getMember();
			member.setSubscription(subscription);
			member.getAccount().setExpiredAt(member.getAccount().getExpiredAt().plusMonths(subscription.getPlan().getMonths()));
			subscription.setStartAt(LocalDate.now());
		}
	}

	private List<Subscription> findApprovedSubscriptions() {
		return repo.search(cb -> {
			var cq = cb.createQuery(Subscription.class);
			var root = cq.from(Subscription.class);
			var account = root.join(Subscription_.member)
					.join(Member_.account);

			cq.select(root);
			
			cq.where(
				cb.equal(root.get(Subscription_.status), Status.Approved),
				cb.isNull(root.get(Subscription_.startAt)),
				cb.lessThan(account.get(Account_.expiredAt), LocalDate.now())
			);
			
			return cq;
		});
	}
}
