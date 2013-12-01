package com.kt.smnw.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.kt.naas.util.BWUtils;

public class TestBWUtils {

	@Test
	public void testBWUtils() {
		String strBw = "200Mbps";
		int bwBytes = 100000;
		
		System.out.println("*** BW= " + strBw);
		int bw = BWUtils.bwToInt(strBw);
		
		System.out.println("*** BW= " + bw);
		
		System.out.println("*** BW= " + bwBytes);
		strBw = BWUtils.bwToStr(bwBytes);
		
		System.out.println("*** BW= " + strBw);
	}

}
