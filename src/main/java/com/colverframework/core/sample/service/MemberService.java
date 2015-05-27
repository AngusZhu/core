package com.colverframework.core.sample.service;

import com.colverframework.core.sample.model.Member;

public interface MemberService {

	void add(Member member);

	void delete(String id);

	Member get(String id);

}
