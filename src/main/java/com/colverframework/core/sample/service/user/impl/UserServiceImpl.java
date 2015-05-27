package com.colverframework.core.sample.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.colverframework.core.sample.dao.user.UserDao;
import com.colverframework.core.sample.pojo.User;
import com.colverframework.core.sample.service.user.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	@Qualifier("userDaoMongoImpl")
	private UserDao userDao;
	
	@Override
	public User getUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String name, String password) {
		return userDao.getUser(name,password);
	}

	@Override
	public int getUserCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateUser(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUser(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getUser(User user) {
		return getUser(user.getName(),user.getPassword());
	}

	@Override
	public boolean saveUser(User user) {
		return userDao.saveUser( user);
	}

}
