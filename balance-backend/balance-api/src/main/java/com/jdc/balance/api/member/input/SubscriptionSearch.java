package com.jdc.balance.api.member.input;

import java.time.LocalDate;

import com.jdc.balance.domain.entity.Subscription;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public record SubscriptionSearch(
		Integer planId,
		LocalDate appliedFrom,
		LocalDate appliedTo) {

	public Expression<Boolean> where(CriteriaBuilder cb, Root<Subscription> root) {
		// TODO Auto-generated method stub
		return null;
	}

}
