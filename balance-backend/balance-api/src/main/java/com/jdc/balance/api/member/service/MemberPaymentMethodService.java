package com.jdc.balance.api.member.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.member.output.PaymentMethodListItem;
import com.jdc.balance.domain.entity.PaymentMethod;
import com.jdc.balance.domain.entity.PaymentMethod_;
import com.jdc.balance.domain.repo.PaymentMethodRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('Member')")
public class MemberPaymentMethodService {
	
	private final PaymentMethodRepo repo;
	
	@Transactional(readOnly = true)
	public List<PaymentMethodListItem> getAvailablePayments() {
		return repo.search(cb -> {
			var cq = cb.createQuery(PaymentMethodListItem.class);
			
			var root = cq.from(PaymentMethod.class);
			PaymentMethodListItem.select(cq, root);
			cq.where(cb.isTrue(root.get(PaymentMethod_.active)));
			
			cq.orderBy(cb.asc(root.get(PaymentMethod_.id)));
			
			return cq;
		});
	}

}
