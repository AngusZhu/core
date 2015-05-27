package com.cloverframework.core.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
	public static void main(String[] args) {
		new PrimeNumber().calculate();
	}
}
class PrimeNumber{
	private static final int TOTAL_COUNT=500;
	private static final int RANGE_LENGTH=200;
	private static final int WORKER_NUMBER=5;
	private static volatile boolean done=false;
	private static int rangeCount=0;
	private static final List<Long> result=new ArrayList<Long>();
	

	private static final CyclicBarrier barrier=new CyclicBarrier(WORKER_NUMBER,new Runnable(){
		@Override
		public void run() {
			System.out.println("result size:"+result.size());
			if(result.size()>=TOTAL_COUNT){
				done=true;
			}
		}
	});
		class PerimeFinder implements Runnable{
			@Override
			public void run() {
				while(!done){
					int range=getNextRange();
					long start =range*RANGE_LENGTH;
					long end=(range+1)*RANGE_LENGTH;
					System.out.println("start is :"+start);
					System.out.println("end is :"+end);
					for(long i =start;i<end;i++){
						if(isPrime(i)){
							updateResult(i);
						}
					}
					try {
						barrier.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						done=true;
					}
				}
			}

			private synchronized void updateResult(long i) {
				result.add(i);
			}

			private boolean isPrime(long i) {
				boolean isprime=true;
				for(int j=2 ;j<=Math.sqrt(i);j++){
					if(i%j==0){
						isprime=false;
					}
				}if(isprime){
					System.out.println(i+" is prime"+isprime);
				}
				return isprime;
			}
			private synchronized int getNextRange() {
				return rangeCount++;
			}
		}
		
		public void calculate(){
			for(int i=0;i<WORKER_NUMBER;i++){
				new Thread(new PerimeFinder()).start();
			}
			while(!done){
				
			}
			System.out.println(Arrays.deepToString(result.toArray()));
		}
		
	
	
	
}