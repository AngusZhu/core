package com.cloverframework.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * VM args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 
 * @author AngusZhu
 */
public class OOMTest {

	static class OOMObject {

	}

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<OOMObject>();
		while (true) {
			list.add(new OOMObject());
		}
	}

}
