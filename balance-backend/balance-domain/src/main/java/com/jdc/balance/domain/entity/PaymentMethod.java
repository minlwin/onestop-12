package com.jdc.balance.domain.entity;

import java.util.List;

import com.jdc.balance.domain.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class PaymentMethod extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String accountNo;
	@Column(nullable = false)
	private String accountName;
	private boolean active;
	
	@OneToMany(mappedBy = "payment")
	private List<Subscription> subscription;
	
}
