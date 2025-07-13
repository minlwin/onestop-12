package com.jdc.balance.domain;

import java.util.ArrayList;
import java.util.List;

public record Pager(int page, int size, long totalCount) {

	public long getTotalPage() {
		var pages = totalCount / size;
		return totalCount % size == 0 ? pages : pages + 1;
	}
	
	public List<Integer> getLinks() {
		var lastPage = getTotalPage() - 1;
		
		var links = new ArrayList<Integer>();
		links.add(page);
		
		while (links.size() < 3 && links.getFirst() > 0) {
			links.addFirst(links.getFirst() - 1);
		}
		
		while (links.size() < 5 && links.getLast() < lastPage) {
			links.add(links.getLast() + 1);
		}
		
		while (links.size() < 5 && links.getFirst() > 0) {
			links.addFirst(links.getFirst() - 1);
		}

		return links;	
	}
}
