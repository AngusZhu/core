package com.colverframework.core.sample.dao.user.impl;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.colverframework.core.sample.dao.user.UserDao;
import com.colverframework.core.sample.pojo.User;

@Repository("userDaoMongoImpl")
public class UserDaoMongoImpl implements UserDao{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public User getUser(String name, String password) {
		Query query = new Query();
		Criteria criteria = Criteria.where("name").is(name).where("password").is(password);
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, User.class);
	}

	@Override
	public boolean saveUser(User user) {
		mongoTemplate.save(user,"user");
		return true;
	}
	
}