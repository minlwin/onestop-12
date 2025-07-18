package com.jdc.balance.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.cutoff.day")
public class CutOffDayProperties {

	private int value;
}
