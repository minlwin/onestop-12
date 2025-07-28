package com.jdc.balance.domain.repo;

import java.util.Optional;

import com.jdc.balance.domain.BaseRepository;
import com.jdc.balance.domain.embeddable.SubscriptionPk;
import com.jdc.balance.domain.entity.Subscription;

public interface SubscriptionRepo extends BaseRepository<Subscription, SubscriptionPk>{

	Optional<Subscription> findFirstByOrderByCreatedAt();
}
