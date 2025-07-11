package com.jdc.balance.api.management.service;

import static com.jdc.balance.common.utils.EntityOperations.safeCall;

import java.util.List;
import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.management.input.PaymentMethodForm;
import com.jdc.balance.api.management.input.PaymentMethodSearch;
import com.jdc.balance.api.management.output.PaymentMethodDetails;
import com.jdc.balance.api.management.output.PaymentMethodListItem;
import com.jdc.balance.common.dto.ModificationResult;
import com.jdc.balance.domain.entity.PaymentMethod;
import com.jdc.balance.domain.entity.PaymentMethod_;
import com.jdc.balance.domain.repo.PaymentMethodRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@PreAuthorize("hasAuthority('Admin')")
public class PaymentMethodService {
	
	private static final String ENTITY_TYPE = "Payment method";
	
	private final PaymentMethodRepo repo;

	@Transactional
	public ModificationResult<Integer> create(PaymentMethodForm form) {
		var entity = repo.save(form.entity());
		return ModificationResult.success(entity.getId());
	}

	@Transactional
	public ModificationResult<Integer> update(int id, PaymentMethodForm form) {
		var entity = safeCall(repo.findById(id), ENTITY_TYPE, id);
		form.update(entity);
		return ModificationResult.success(entity.getId());
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
			cq.orderBy(cb.asc(root.get(PaymentMethod_.id)));
			return cq;
		};
	}

}
