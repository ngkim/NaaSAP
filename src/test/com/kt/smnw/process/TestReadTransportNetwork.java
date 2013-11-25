package com.kt.smnw.process;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.util.RequestClient;
import com.kt.naas.xml.RequestInfoEthernet;
import com.kt.naas.xml.ResponseInfoEthernet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestReadTransportNetwork {
	@Autowired
	private RequestClient requestClient;

	@Test
	public void testReadTransportNetwork() {
		RequestInfoEthernet req = new RequestInfoEthernet();
		ResponseInfoEthernet res = null;
		
		try {
			req.setRid("NaaS");
			req.setCid("NH_ADMIN");
			req.setEid("ethernet service id");
			
			TransportSDNAPI api = new TransportSDNAPI(GlobalConstants.URL_TRANSPORT_SDN_API);
			res = api.readNetwork(req);
			
			api.printResponseInfoEthernet(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
