package com.cloverframework.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * vm args:  -XX:PermSize2m -XX:MaxPermSize2m
 * @author AngusZhu
 *
 */
public class PermOOM {

	public static void main(String[] args) {
		 List<String> list=new ArrayList<String>();
		 Random random = new Random();
		 while(true){
			 list.add(String.valueOf(random.nextInt(10000000)).intern());
		 }
	}
	
}
