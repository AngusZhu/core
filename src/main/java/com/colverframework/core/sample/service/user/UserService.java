package com.colverframework.core.sample.service.user;

import java.util.List;

import com.colverframework.core.sample.pojo.User;

public interface UserService {

	public User getUser(String id);
	
	public User getUser(User User);
	
	public User getUser(String name,String password);
	
	public int getUserCount();
	
	public List<User> getAllUser();
	 
	public int updateUser(String id);
	
	public int deleteUser(String id);

	public boolean saveUser(User user);
	
	
	 
	 
	
}
