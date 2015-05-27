package com.colverframework.core.sample.mongo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConstantUtils {
	static Properties  p=new Properties();
	static {
		InputStream in = ConstantUtils.class.getResourceAsStream("C://Users//IBM_ADMIN//webspace//core//webapp//WEB-INF//property//mongo.properties");
		try {
			p.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getValue(String string) {
		return (String)p.get(string);
	}

	
	
}
