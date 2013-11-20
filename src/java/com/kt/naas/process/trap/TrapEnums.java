package com.kt.naas.process.trap;

public interface TrapEnums {
	
	public static final String[] CATEGORY_NAME = new String[] {
		"-","Communications","Service Quality","Processing Error","Equipment","Environment","Content"
	};	
	public static final int[]	CATEGORY_INDEX = new int[] {
		0, 1, 2, 3, 4, 5, 6
	};	
	public static final String[] P_NAME = new String[] {
		"-","SG","SN"
	};	
	public static final int[]	P_INDEX = new int[] {
		0, 1, 2
	};	
	public static final String[] SG_MM_NAME = new String[] {
		"-","MAIN","TCPD","SR","OM","PS","SSN"
	};	
	public static final int[]	SG_MM_INDEX = new int[] {
		0, 1, 2, 3, 4, 5, 6
	};	
	public static final String[] SN_MM_NAME = new String[] {
		"-","MAIN","TCPD","CACHING","DELETE","EMS","LOG","DB","DHT"
	};	
	public static final int[]	SN_MM_INDEX = new int[] {
		0, 1, 2, 3, 4, 5, 6, 7, 8
	};
}