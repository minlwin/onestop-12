package com.jdc.balance.domain.repo;

import java.util.Optional;

import com.jdc.balance.domain.BaseRepository;
import com.jdc.balance.domain.entity.Member;

public interface MemberRepo extends BaseRepository<Member, Long>{

	Optional<Member> findByAccountEmail(String username);

}
