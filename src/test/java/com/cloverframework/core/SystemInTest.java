package com.cloverframework.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemInTest {
		   public static void main(String[] args) {
		        String str = "";
		        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		        System.out.print("请输入字符串:");
		        try {
		            str = br.readLine();
		            System.out.println();
		            System.out.println("您输入的字符串为:"+str);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        
		    }
}
