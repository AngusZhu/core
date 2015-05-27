package com.cloverframework.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class BTraceTest {
	public int add(int a ,int b){
		return a+b;
	}
	public static void main(String[] args) throws IOException {
		BTraceTest bt=new BTraceTest();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		for(int i=0;i<10;i++){
			br.readLine();
			int a=new Random().nextInt(1000);
			int b=new Random().nextInt(1000);
			System.out.println(bt.add(a, b));
		}
	}

}
