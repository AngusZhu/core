package com.colverframework.core.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colverframework.core.sample.dao.MemberDao;
import com.colverframework.core.sample.model.Member;
@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	
	public void add(Member member) {
		memberDao.add(member);
	}
	public void delete(String id) {
		// TODO Auto-generated method stub
		memberDao.delete(id);
	}

	public Member get(String id) {
		return memberDao.getMember(id);
	}

}
