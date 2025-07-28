package com.jdc.balance.common.dto;

import com.jdc.balance.domain.entity.Subscription.Status;

public record SubscriptionStatusSummary(Status status, String planName, Long count) {
}
