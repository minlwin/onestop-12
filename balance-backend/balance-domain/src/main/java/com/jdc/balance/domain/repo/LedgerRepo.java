package com.jdc.balance.domain.repo;

import org.springframework.data.jpa.repository.Query;

import com.jdc.balance.domain.BaseRepository;
import com.jdc.balance.domain.embeddable.LedgerPk;
import com.jdc.balance.domain.entity.Ledger;

public interface LedgerRepo extends BaseRepository<Ledger, LedgerPk>{

	@Query("select count(l) from Ledger l where l.id.memberId = :id")
	Long countForCheck(long id);

}
