package com.jdc.balance.api.management.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.management.input.PaymentMethodForm;
import com.jdc.balance.api.management.input.PaymentMethodSearch;
import com.jdc.balance.api.management.output.PaymentMethodDetails;
import com.jdc.balance.api.management.output.PaymentMethodListItem;
import com.jdc.balance.domain.entity.PaymentMethod;
import com.jdc.balance.domain.repo.PaymentMethodRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentMethodService {
	
	private static final String ENTITY_TYPE = "Payment method";
	
	private final PaymentMethodRepo repo;

	@Transactional
	public PaymentMethodDetails create(PaymentMethodForm form) {
		var entity = repo.save(form.entity());
		return PaymentMethodDetails.from(entity);
	}

	@Transactional
	public PaymentMethodDetails update(int id, PaymentMethodForm form) {
		var entity = safeCall(repo.findById(id), ENTITY_TYPE, id);
		form.update(entity);
		return PaymentMethodDetails.from(entity);
	}

	public PaymentMethodDetails findById(int id) {
		var entity = safeCall(repo.findById(id), ENTITY_TYPE, id);
		return PaymentMethodDetails.from(entity);
	}

	public List<PaymentMethodListItem> search(PaymentMethodSearch search) {
		return repo.search(queryFunc(search));
	}

	private Function<CriteriaBuilder, CriteriaQuery<PaymentMethodListItem>> queryFunc(PaymentMethodSearch search) {
		return cb -> {
			var cq = cb.createQuery(PaymentMethodListItem.class);
			var root = cq.from(PaymentMethod.class);
			PaymentMethodListItem.select(cb, cq, root);
			cq.where(search.where(cb, root));
			return cq;
		};
	}

}
