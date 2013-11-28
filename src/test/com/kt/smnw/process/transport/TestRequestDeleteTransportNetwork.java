package com.kt.smnw.process.transport;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.util.RequestClient;
import com.kt.naas.util.RestAPIUtils;
import com.kt.naas.xml.QoS;
import com.kt.naas.xml.RequestCreateTransportNetwork;
import com.kt.naas.xml.RequestDeleteTransportNetwork;
import com.kt.naas.xml.UNIPeer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestRequestDeleteTransportNetwork {
	@Autowired
	private RequestClient requestClient;

	@Test
	public void testRequestCreateTransportNetwork() {
		RequestDeleteTransportNetwork req = new RequestDeleteTransportNetwork();
		RestAPIUtils apiUtil = new RestAPIUtils();
		
		try {
			req.setName("myEth");
			req.setDescription("DC to DC tunnel");
			
			req.setRid("requester ID");
			req.setCid("123");
			req.setEid("ethernet service id");
			String xml = apiUtil.getRequestXML(req);
			
			System.out.println(xml);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
