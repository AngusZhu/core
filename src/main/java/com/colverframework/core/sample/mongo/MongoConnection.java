package com.colverframework.core.sample.mongo;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoConnection {

	
	public static void main(String[] args) throws UnknownHostException {
		queryDB();
		createDB();
		
	}

	private static void createDB() throws UnknownHostException {
		MongoClient mongoClient=new MongoClient("localhost", 9999);
		
		
	}

	private static void queryDB() throws UnknownHostException {
		MongoClient mongoClient=new MongoClient("localhost", 9999);
		DB db = mongoClient.getDB("foobar");
		Set<String> collectionNames = db.getCollectionNames();
		for(String a:collectionNames){
			System.out.println("-----------------------collectionName---"+a+"------------------------");
			DBCollection collection = db.getCollection(a);
			DBCursor find = collection.find();
			for(DBObject o:find){
				System.out.println(o.toString());
				System.out.println(o.toMap());
			}
		}
		
	}
}
