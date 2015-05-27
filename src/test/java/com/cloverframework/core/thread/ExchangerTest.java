package com.cloverframework.core.thread;

import java.util.concurrent.Exchanger;

public class ExchangerTest {
	public static void main(String[] args) {
		new SendAndReceive().exchange();
		System.out.println("finished ...");
	}
}
class SendAndReceive{
	
	private final Exchanger<StringBuilder> exchanger=new Exchanger<StringBuilder>();
	
	private class Sender implements Runnable{
		@Override
		public void run() {
			StringBuilder sb=new StringBuilder("Hello");
			try {
				sb=exchanger.exchange(sb);
				System.out.println("sb sender:"+sb.toString());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	private class Receiver implements Runnable{
		@Override
		public void run() {
			StringBuilder sb=new StringBuilder("World");
			try {
				sb=exchanger.exchange(sb);
				System.out.println("sb Receiver:"+sb.toString());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void exchange(){
		new Thread(new Sender()).start();
		new Thread(new Receiver()).start();
		System.out.println(exchanger.toString());
	}
	
}
