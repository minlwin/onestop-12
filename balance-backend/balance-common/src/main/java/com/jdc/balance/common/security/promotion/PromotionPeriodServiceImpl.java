package com.jdc.balance.common.security.promotion;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PromotionPeriodServiceImpl implements PromotionPeriodService{

	@Value("${app.promotion.period.value}")
	private int value;
	@Value("${app.promotion.period.unit}")
	private PromotionPeriod.PeriodUnit unit;
	
	@Override
	public Optional<PromotionPeriod> getPromotionPeriod() {
		
		if(null != unit && value > 0) {
			return Optional.of(new PromotionPeriod(value, unit));
		}
		
		return Optional.empty();
	}

}
