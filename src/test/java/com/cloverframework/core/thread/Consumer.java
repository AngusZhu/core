package com.cloverframework.core.thread;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

public  class Consumer implements Runnable {

	ArrayBlockingQueue<Date> queue;
	public Consumer(ArrayBlockingQueue<Date> queue) {
		this.queue=queue;
	}

	@Override
	public void run() {
		for(int i=0;i<100;i++){
			queue.poll();
			System.out.println("Consumer :"+queue.remainingCapacity());
		}
		
	}

}
