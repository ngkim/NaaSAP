package com.kt.smnw.domain;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.error.DatatypeMismatchException;
import com.kt.naas.error.NoMoreDataException;

public class FieldBufferTest {
	FieldBuffer data = new FieldBuffer();
	
	@Test
	public void testString() {
		data.putString("STR1", "Test String");
		String value = data.getString("STR1");
		assertEquals(value, "Test String");
	}
	
	@Test(expected=NoMoreDataException.class)
	public void testNoData()
	{
		String value = data.getString("STR1");
		System.out.println(value);
	}

	@Test(expected=DatatypeMismatchException.class)
	public void testTypeMismatch()
	{
		data.putString("STR1", "Test String");
		int value = data.getInt("STR1");
		System.out.println(value);
	}

	@Test
	public void testLong() {
		data.putLong("LONG1", 100L);
		long value = data.getLong("LONG1");
		assertEquals(value, 100L);

	}

	@Test
	public void testInt() {
		data.putInt("INT1", 100);
		int value = data.getInt("INT1");
		assertEquals(value, 100);
	}

	@Test
	public void testDouble() {
		data.putDouble("DOUBLE1", 123456789.123456789d);
		double value = data.getDouble("DOUBLE1");
		assertTrue(value == 123456789.123456789d);
	}

	@Test
	public void testGetCount() {
		assertTrue(data.getCount("STR1") == 0);
		
		data.putString("STR1", "Test String");
		assertTrue(data.getCount("STR1") == 1);
		String value = data.getString("STR1");
		assertEquals(value, "Test String");
		assertTrue(data.getCount("STR1") == 0);
		data.putString("STR1", "Test String1");
		data.putString("STR1", "Test String2");
		data.putString("STR1", "Test String3");
		data.putString("STR1", "Test String4");
		data.putString("STR1", "Test String5");
		
		value = data.getString("STR1");
		assertEquals(value, "Test String1");

		value = data.getString("STR1");
		assertEquals(value, "Test String2");
		
		assertTrue(data.getCount("STR1") == 3);
	}
	
	@Test
	public void testToString() {
		data.putString("STR1", "Test String1");
		data.putString("STR1", "Test String2");
		data.putString("STR1", "Test String3");
		data.putString("STR1", "Test String4");
		data.putString("STR1", "Test String5");
		data.putDouble("DOUBLE1", 123456789.123456789d);
		data.putDouble("DOUBLE1", 123456789.123456789d);
		data.putInt("INT1", 100);
		data.putInt("INT1", 100);
		data.putInt("INT1", 100);

		System.out.println(data);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testPutMap()
	{
		Map map = new HashMap();
		map.put("STR1", "String");
		map.put("STR2", "String");
		map.put("LONG1", new Long(123456789L));
		data.putMap(map);
		System.out.println(data);
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("STR1", "StringX");
		m.put("STR2", "StringX");
		m.put("LONG1", new Long(12345678900L));
		data.putMap(m);
		
		System.out.println(data);
	}
}
