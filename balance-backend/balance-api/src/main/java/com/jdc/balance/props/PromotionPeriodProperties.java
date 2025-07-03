package com.jdc.balance.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.jdc.balance.common.security.promotion.PromotionPeriod;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.promotion.period")
public class PromotionPeriodProperties {

	private PromotionPeriod.PeriodUnit unit;
	private int value;
}
