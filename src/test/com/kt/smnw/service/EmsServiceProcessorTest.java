package com.kt.smnw.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.domain.SnResult;
import com.kt.naas.domain.decoder.SnResultDecoder;
import com.kt.naas.service.EmsServiceProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/application-context.xml")
public class EmsServiceProcessorTest {

	@Test
	public void testPolling() {
		EmsServiceProcessor ems = new EmsServiceProcessor();
		Map<String, String> params = new HashMap<String, String>();
		params.put("param", "all");
		
		String result = ems.polling("ciscoems","com.cisco.unicorn.ui.ListApiServlet", "getDeliveryServices", params);
		
		SnResultDecoder decoder = new SnResultDecoder();
		SnResult snResult = decoder.parse(result);
		assertEquals(snResult.getStatus(), "success");
		System.out.println("EMS Result=" + snResult);
	}

}
