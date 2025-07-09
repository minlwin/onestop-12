package com.jdc.balance.api.management.service;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.jdc.balance.api.management.input.MemberSearch;
import com.jdc.balance.api.management.output.MemberListItem;
import com.jdc.balance.domain.PageResult;
import com.jdc.balance.domain.entity.Account_;
import com.jdc.balance.domain.entity.Member;
import com.jdc.balance.domain.entity.Member_;
import com.jdc.balance.domain.repo.MemberRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepo memberRepo;

	public PageResult<MemberListItem> search(MemberSearch search, int page, int size) {
		return memberRepo.searchPage(queryFunc(search), countFunc(search), page, size);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<MemberListItem>> queryFunc(MemberSearch search) {
		return cb -> {
			var cq = cb.createQuery(MemberListItem.class);
			var root = cq.from(Member.class);
			MemberListItem.select(cq, root);
			cq.where(search.where(cb, root));
			cq.orderBy(cb.desc(root.get(Member_.account).get(Account_.expiredAt)));
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(MemberSearch search) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Member.class);
			cq.where(search.where(cb, root));
			cq.select(cb.count(root.get(Member_.id)));
			return cq;
		};
	}
}
