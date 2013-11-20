package com.kt.smnw.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.kt.naas.util.StringUtils;

public class StringUtilsTest {

	@Test
	public void testChangeDomain() {
		String url = "http://cfvodg.cds.ktsn.com/cfvod/cf0004_720_Stream2.m3u8";
		String hope = "http://50.50.50.50/cfvod/cf0004_720_Stream2.m3u8";
		String changed = StringUtils.changeDomain(url, "50.50.50.50");
		
		System.out.println("URL=[" + url + "]");
		System.out.println("CHANGED=[" + changed + "]");
		
		assertEquals(changed, hope);
	}

	@Test 
	public void testGetFixedSizeString() {
		String result = StringUtils.getFixedSizeString("test", '_', 10);
		System.out.println(result);
		assertEquals("test______", result);
	}
	
	@Test
	public void testMakeOpenProductId()
	{
		System.out.println(StringUtils.makeOpenProductId("Channel_1313", "ENTER", "kbs_Channel_1313_20110915130005_1236", ""));
		System.out.println(StringUtils.makeOpenProductId("Channel_1313", "ENTER", "kbs_Channel_1313_20110915130005_1239", ""));
		System.out.println(StringUtils.makeOpenProductId("Channel_1313", "REPORT", "kbs_Channel_1313_20110915130005_1241", ""));
	}
}
