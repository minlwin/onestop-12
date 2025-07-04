package com.jdc.balance.api.management;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdc.balance.api.management.input.SubscriptionPlanForm;
import com.jdc.balance.api.management.input.SubscriptionPlanSearch;
import com.jdc.balance.api.management.output.SubscriptionPlanDetails;
import com.jdc.balance.api.management.output.SubscriptionPlanListItem;

@RestController
@RequestMapping("management/plan")
public class SubscrptionPlanApi {

	@GetMapping
	List<SubscriptionPlanListItem> search(SubscriptionPlanSearch search) {
		return null;
	}
	
	@PostMapping
	SubscriptionPlanDetails create(@Validated @RequestBody SubscriptionPlanForm form) {
		return null;
	}
	
	@PutMapping("{id}")
	SubscriptionPlanDetails update(@PathVariable int id, @Validated @RequestBody SubscriptionPlanForm form) {
		return null;
	}
	
	@GetMapping("{id}")
	SubscriptionPlanDetails findById(@PathVariable int id) {
		return null;
	}
}
