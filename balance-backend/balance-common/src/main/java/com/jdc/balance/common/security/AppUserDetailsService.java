package com.jdc.balance.common.security;

import java.time.LocalDate;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jdc.balance.domain.entity.Account;
import com.jdc.balance.domain.repo.AccountRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService{

	private final AccountRepo accountRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return accountRepo.findById(username)
				.map(account -> User.builder()
						.username(username)
						.password(account.getPassword())
						.authorities(new SimpleGrantedAuthority(account.getRole().name()))
						.accountExpired(isExpired(account))
						.build())
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}

	private boolean isExpired(Account account) {
		
		if(null != account.getExpiredAt()) {
			return LocalDate.now().compareTo(account.getExpiredAt()) >= 0;
		}

		return true;
	}

}
