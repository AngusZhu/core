package com.colverframework.core.sample.publish;

import org.apache.commons.lang.math.RandomUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class Publish {

		public static void main(String[] args) {
			Jedis jedis= new Jedis("192.168.1.120", 6379);
			Pipeline pipelined = jedis.pipelined();
			while(true){
				jedis.publish("message", String.valueOf(RandomUtils.nextDouble()));
			}
		}
}
