package com.kt.smnw.process;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.DJPremiseSDNAPI;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.api.WMPremiseSDNAPI;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.TimeUtils;
import com.kt.naas.xml.RequestCreatePremiseNetwork;
import com.kt.naas.xml.RequestCreateTransportNetwork;
import com.kt.naas.xml.RequestDeletePremiseNetwork;
import com.kt.naas.xml.RequestPremiseNWList;
import com.kt.naas.xml.ResponseCreatePremiseNetwork;
import com.kt.naas.xml.ResponseCreateTransportNetwork;
import com.kt.naas.xml.ResponseDeletePremiseNetwork;
import com.kt.naas.xml.ResponsePremiseNWList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestDeleteDJPremiseNetwork {
	@Autowired
	private RequestClient requestClient;
	
	private RequestDeletePremiseNetwork generateRequest() {
		RequestDeletePremiseNetwork reqPremiseNW = new RequestDeletePremiseNetwork();
			
		reqPremiseNW.setTenantname("NH_ADMIN");
		reqPremiseNW.setCpSvcId("CSDN000001");
		
		return reqPremiseNW;		
	}

	@Test
	public void testDeleteDJPremiseNetwork() {
		ResponseDeletePremiseNetwork resPremiseNW = null;
		TimeUtils time = new TimeUtils();
		
		try {
			time.setStartTime();
			RequestDeletePremiseNetwork reqPremiseNW = generateRequest();
			
			DJPremiseSDNAPI api = new DJPremiseSDNAPI(GlobalConstants.URL_PREMISE_SDN_API_DJ);
			resPremiseNW = api.deleteNetwork(reqPremiseNW);
			
			double duration = time.getDuration();
			System.err.println("\nTime for reading a premise network = " + duration + " ms");
			
			api.printResponseDeletePremiseNetwork(resPremiseNW);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
