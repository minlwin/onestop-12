package com.jdc.balance.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.domain.embeddable.LedgerEntryPk;
import com.jdc.balance.domain.entity.LedgerEntrySeq;
import com.jdc.balance.domain.repo.LedgerEntrySeqRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LedgerEntryIdGenerator {

	private final LedgerEntrySeqRepo seqRepo;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public LedgerEntryPk next(long memberId, String code) {
		
		var key = String.format("%s-%s", memberId, code);
		
		var seq = seqRepo.findById(key).orElseGet(() -> {
			var entity = new LedgerEntrySeq();
			entity.setSeqKey(key);
			return seqRepo.create(entity);
		});
		
		return new LedgerEntryPk(code, memberId, seq.nextSeq());
	}
}
