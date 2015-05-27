package com.colverframework.core.sample.dao.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import com.colverframework.core.sample.base.RedisGeneratorDao;
import com.colverframework.core.sample.dao.user.UserDao;
import com.colverframework.core.sample.pojo.User;

@Repository("userDaoRedisImpl")
public class UserDaoRedisImpl  extends RedisGeneratorDao<String,User> implements UserDao {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public User getUser(final String name,final  String password) {
		  return new User();
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public boolean saveUser(final User user) {
		return (boolean) redisTemplate.execute(new RedisCallback<Boolean>(){
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				   RedisSerializer<String> serializer = getRedisSerializer();  
	                byte[] key  = serializer.serialize(user.getId());  
	                byte[] name = serializer.serialize(user.getName());  
	                return connection.setNX(key, name);  
			}
		});
	}
}