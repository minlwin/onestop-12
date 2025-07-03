package com.jdc.balance.common.security.promotion;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.jdc.balance.common.security.promotion.PromotionPeriod.PeriodUnit;

@Service
@Primary
public class PromotionPeriodServiceDefault implements PromotionPeriodService{

	@Override
	public Optional<PromotionPeriod> getPromotionPeriod() {
		return Optional.of(new PromotionPeriod(1, PeriodUnit.Month));
	}

}
