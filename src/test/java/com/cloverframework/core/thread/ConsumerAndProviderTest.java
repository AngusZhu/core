package com.cloverframework.core.thread;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

public class ConsumerAndProviderTest {
	
	public static final int MAX_SIZE=10;
	public static void main(String[] args) {
		ArrayBlockingQueue<Date> queue=new ArrayBlockingQueue<Date>(10);
		new Thread(new Provider(queue)).start();
		new Thread(new Consumer(queue)).start();
	}
}
