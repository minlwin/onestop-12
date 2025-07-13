package com.jdc.balance.common.utils;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdc.balance.common.dto.LedgerEntryItem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LedgerEntryItemMapper {

	private final ObjectMapper objectMapper;

	public String toJson(List<LedgerEntryItem> items) {
		try {
			return objectMapper.writeValueAsString(items);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error converting LedgerEntryItem list to JSON", e);
		}
	}
	
	public List<LedgerEntryItem> fromJson(String json) {
		try {
			return objectMapper.readValue(json, new TypeReference<List<LedgerEntryItem>>() {});
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error converting JSON to LedgerEntryItem list", e);
		}
	}
}
