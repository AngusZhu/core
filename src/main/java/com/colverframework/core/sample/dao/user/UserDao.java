package com.colverframework.core.sample.dao.user;

import com.colverframework.core.sample.pojo.User;

public interface UserDao {

	User getUser(String name, String password);

	boolean saveUser(User user);

	
}
