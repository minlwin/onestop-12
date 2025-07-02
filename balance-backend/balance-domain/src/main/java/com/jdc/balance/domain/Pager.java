package com.jdc.balance.domain;

import java.util.List;

public record Pager(int page, int size, long totalCount) {

	public long getTotalPage() {
		var pages = totalCount / size;
		return totalCount % size == 0 ? pages : pages + 1;
	}
	
	public List<Integer> getLinks() {
		// TODO
		return null;
	}
}
