package com.kt.smnw.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.kt.naas.util.Base64Utils;

public class Base64UtilsTest {

	private final static byte[] plain = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".getBytes();
	private final static String encoded = "QUJDREVGR0hJSktMTU5PUFFSU1RVVldYWVphYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5ejEyMzQ1Njc4OTA=";
	
	@Test
	public void testBase64Encode() {
		//System.out.println("encoded = [" + Base64Utils.base64Encode(plain) + "]");
		assertEquals(Base64Utils.base64Encode(plain), encoded);
	}

	@Test
	public void testBase64Decode() {
		byte[] decoded = Base64Utils.base64Decode(encoded);
		assertTrue(decoded.length == plain.length);
		
		for (int i = 0; i < decoded.length; i++) {
			assertTrue(decoded[i] == plain[i]);	
		}
	}

	@Test
	public void testEncodeLoop64() {
		for (int i = 0; i < 100000; i++) 	testBase64Encode();
	}

	@Test
	public void testDecodeLoop64() {
		for (int i = 0; i < 100000; i++) 	testBase64Decode();
	}
}
