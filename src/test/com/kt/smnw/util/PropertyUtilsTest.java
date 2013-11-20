package com.kt.smnw.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.util.PropertyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-context.xml")
public class PropertyUtilsTest {
	private final static String key = "TEST_PROPERTY_KEY";
	private final static String value1 = "TEST_PROPERTY_VALUE1";
	private final static String value2 = "TEST_PROPERTY_VALUE2";
	
	@Test
	public void testPropertyUtils() {
		System.out.println("SNMP.READTIMEOUT=" + PropertyUtils.getString("SNMP.READTIMEOUT"));
		
		//¸ÕÀú db, file property ¾ø¾Ø´Ù.
		PropertyUtils.removeDbProperty(key);
		PropertyUtils.removeFileProperty(key);
		
		PropertyUtils.setFileProperty(key, value1);
		assertEquals(value1, PropertyUtils.getString(key));
		
		PropertyUtils.setDbProperty(key, value2);
		assertEquals(value2, PropertyUtils.getString(key));
		
		PropertyUtils.removeDbProperty(key);
		assertEquals(value1, PropertyUtils.getString(key));
		
		PropertyUtils.removeFileProperty(key);
		assertNull(PropertyUtils.getString(key));
	}

}
