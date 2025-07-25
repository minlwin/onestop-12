package com.jdc.balance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.jdc.balance.domain.repo.AccountRepo;

@SpringBootTest
@ActiveProfiles("test")
public class BalanceApiApplicationTest {
	
	@Autowired
	private AccountRepo accountRepo;

	@Test
	public void launchApplicatio() {
		var count = accountRepo.count();
		assertEquals(1L, count);
	}
}
