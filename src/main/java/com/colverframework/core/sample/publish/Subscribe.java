package com.colverframework.core.sample.publish;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Subscribe {

	public static void main(String[] args) {
		Jedis jedis= new Jedis("192.168.1.120", 6379);
		JedisPubSub jps=new JedisPubSub() {
			@Override
			public void onUnsubscribe(String arg0, int arg1) {
				// TODO Auto-generated method stub
				System.out.println("on onUnsubscribe: arg0="+arg0);
				System.out.println("on onUnsubscribe: arg1="+arg1);
			}
			
			@Override
			public void onSubscribe(String arg0, int arg1) {
				// TODO Auto-generated method stub
				System.out.println("on onSubscribe: arg0="+arg0);
				System.out.println("on onSubscribe: arg1="+arg1);
			}
			
			@Override
			public void onPUnsubscribe(String arg0, int arg1) {
				// TODO Auto-generated method stub
				System.out.println("on onPUnsubscribe: arg0="+arg0);
				System.out.println("on onPUnsubscribe: arg1="+arg1);
			}
			
			@Override
			public void onPSubscribe(String arg0, int arg1) {
				// TODO Auto-generated method stub
				System.out.println("on onPSubscribe: arg0="+arg0);
				System.out.println("on onPSubscribe: arg1="+arg1);
			}
			
			@Override
			public void onPMessage(String arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				System.out.println("on onPMessage: arg0="+arg0);
				System.out.println("on onPMessage: arg1="+arg1);
			}
			
			@Override
			public void onMessage(String arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println("on Message: arg0="+arg0);
				System.out.println("on Message: arg1="+arg1);
			}
		};
		jedis.subscribe(jps, "message");
	}
}
