package com.kt.smnw.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.kt.naas.util.SRUtils;

public class SRUtilsTest {
	
	@Test
	public void testRouteURL() {
		String svcUrl = SRUtils.routeURL("127.0.0.1", "http://os3.cds.ktsn.com/demo/4minute_720p.mp4", "10.10.30.23");
		System.out.println("["+svcUrl+"]");
	}

}
