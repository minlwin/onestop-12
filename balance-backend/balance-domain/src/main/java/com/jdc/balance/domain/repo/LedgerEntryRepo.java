package com.jdc.balance.domain.repo;

import com.jdc.balance.domain.BaseRepository;
import com.jdc.balance.domain.embeddable.LedgerEntryPk;
import com.jdc.balance.domain.entity.LedgerEntry;

public interface LedgerEntryRepo extends BaseRepository<LedgerEntry, LedgerEntryPk>{

}
