package com.jdc.balance.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.token")
public class JwtTokenProperties {

	private String issuer;
	private String audiance;
	private int accessLife;
	private int refreshLife;
}
