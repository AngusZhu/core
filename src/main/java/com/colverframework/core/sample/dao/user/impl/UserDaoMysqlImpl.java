package com.colverframework.core.sample.dao.user.impl;

import org.springframework.stereotype.Repository;

import com.colverframework.core.sample.dao.user.UserDao;
import com.colverframework.core.sample.pojo.User;
@Repository("userDaoMysqlImpl")
public class UserDaoMysqlImpl implements UserDao{

	@Override
	public User getUser(String name, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
