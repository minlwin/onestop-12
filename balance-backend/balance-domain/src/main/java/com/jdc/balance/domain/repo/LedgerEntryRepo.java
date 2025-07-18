package com.jdc.balance.domain.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;

import com.jdc.balance.domain.BaseRepository;
import com.jdc.balance.domain.embeddable.LedgerEntryPk;
import com.jdc.balance.domain.entity.LedgerEntry;

public interface LedgerEntryRepo extends BaseRepository<LedgerEntry, LedgerEntryPk>{

	@Query("select count(e.id.code) from LedgerEntry e where e.id.memberId = :memberId and e.issueAt >= :dateFrom")
	Long countForCheck(long memberId, LocalDate dateFrom);

}
