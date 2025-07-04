package com.jdc.balance.api.management.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.management.input.PaymentMethodForm;
import com.jdc.balance.api.management.input.PaymentMethodSearch;
import com.jdc.balance.api.management.output.PaymentMethodDetails;
import com.jdc.balance.api.management.output.PaymentMethodListItem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentMethodService {
	
	

	public List<PaymentMethodListItem> search(PaymentMethodSearch search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public PaymentMethodDetails create(PaymentMethodForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public PaymentMethodDetails update(int id, PaymentMethodForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	public PaymentMethodDetails findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
