package com.cloverframework.core.thread;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

public class Provider implements Runnable{
	private ArrayBlockingQueue<Date> queue;
	
	public Provider(ArrayBlockingQueue<Date> queue) {
		this.queue=queue;
	}

	@Override
	public void run() {
		for(int i=0;i<=100;i++){
			queue.offer(new Date());
			System.out.println(queue.remainingCapacity());
		}
	}

}
