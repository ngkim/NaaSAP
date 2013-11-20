package com.kt.smnw.domain.ems.decoder;

import static org.junit.Assert.*;

import org.junit.Test;

import com.kt.naas.domain.decoder.RouteURLDecoder;

public class RouteURLDecoderTest {
	
	@Test
	public void testParse() {
		RouteURLDecoder decoder = new RouteURLDecoder();
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?> <RoutedURLResponse>  <primaryContentRoutedURL>http://SN-DT-IS1.se.os3.cds.ktsn.com/demo/4minute_720p.mp4</primaryContentRoutedURL> </RoutedURLResponse>";
		String result = decoder.parse(xml);
		System.out.println("["+result+"]");
//		fail("Not yet implemented");
	}

}
