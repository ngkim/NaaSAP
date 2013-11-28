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
import com.kt.naas.xml.ResponseCreateTransportNetwork;
import com.kt.naas.xml.ResponseDeleteTransportNetwork;
import com.kt.naas.xml.TransportStatus;
import com.kt.naas.xml.UNIME;
import com.kt.naas.xml.UNIPeer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestResponseDeleteTransportNetwork {
	@Autowired
	private RequestClient requestClient;

	@Test
	public void testResponseDeleteTransportNetwork() {
		ResponseDeleteTransportNetwork res = new ResponseDeleteTransportNetwork();
		RestAPIUtils apiUtil = new RestAPIUtils();
		
		try {
			res.setId("E-ID");
			res.setName("myEth");
			res.setDescription("DC to DC tunnel");
			
			TransportStatus status = new TransportStatus();
			status.setResult("fail");
			status.setError("PE of switc1-ID is not responding");
			
			res.setStatus(status);
			res.setRid("requester ID");
			res.setCid("123");
			
			String xml = apiUtil.getRequestXML(res);
			
			System.out.println(xml);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
