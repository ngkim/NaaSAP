package com.kt.smnw.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.util.MessageUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-context.xml")
public class MessageUtilsTest {

	@Test
	public void testGetErrorMessage() {
		for (int i = 1; i <= 10; i++) {
			System.out.println("ERROR." + i + "=" + MessageUtils.getErrorMessage(i));	
		}
	}
}
