package com.jdc.balance.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class AuditableEntity {

	@CreatedDate
	private LocalDateTime createdAt;
	@CreatedBy
	private String createdBy;

	@LastModifiedDate
	private LocalDateTime updatedAt;
	@LastModifiedBy
	private String updatedBy;
}
