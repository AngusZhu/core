package com.cloverframework.core;

public class StackOOM {

	private int i = 0;

	public void stackLeak() {
		i++;
		stackLeak();
	}

	public static void main(String[] args) {
		StackOOM soom = new StackOOM();
		try {
			soom.stackLeak();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
