package com.colverframework.core.sample.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.colverframework.core.sample.model.Member;
@Repository("memberDao")
public interface MemberDao {

	boolean add(Member member);
	boolean add(final List<Member> list) ;
	void delete(String key);
	void delete(List<String> keys);
	Member getMember(final String keyId);
}
