package com.jdc.balance.api.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.balance.api.member.input.LedgerForm;
import com.jdc.balance.api.member.input.LedgerSearch;
import com.jdc.balance.api.member.output.LedgerListItem;
import com.jdc.balance.common.dto.ModificationResult;
import com.jdc.balance.domain.embeddable.LedgerPk;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerService {
	
	@Transactional
	public ModificationResult<LedgerPk> create(String username, LedgerForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public ModificationResult<LedgerPk> update(String username, String code, LedgerForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LedgerListItem> search(String username, LedgerSearch search) {
		// TODO Auto-generated method stub
		return null;
	}

}
