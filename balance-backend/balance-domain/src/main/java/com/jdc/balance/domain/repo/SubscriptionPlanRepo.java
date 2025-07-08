package com.jdc.balance.domain.repo;

import java.util.List;

import com.jdc.balance.domain.BaseRepository;
import com.jdc.balance.domain.entity.SubscriptionPlan;

public interface SubscriptionPlanRepo extends BaseRepository<SubscriptionPlan, Integer>{

	List<SubscriptionPlan> findByDefaultPlanAndActive(boolean defaultPlan, boolean active);
}
