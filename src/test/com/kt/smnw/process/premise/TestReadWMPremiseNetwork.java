package com.kt.smnw.process.premise;

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
import com.kt.naas.xml.RequestCreateTransportNetwork;
import com.kt.naas.xml.RequestPremiseNWList;
import com.kt.naas.xml.ResponseCreateTransportNetwork;
import com.kt.naas.xml.ResponsePremiseNWList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestReadWMPremiseNetwork {
	@Autowired
	private RequestClient requestClient;
	
	private RequestPremiseNWList generateRequest() {
		RequestPremiseNWList reqPremiseNW = new RequestPremiseNWList();
		
		reqPremiseNW.setTenantName("NH_ADMIN");
			
		return reqPremiseNW;		
	}

	@Test
	public void testReadWMPremiseNetwork() {
		ResponsePremiseNWList resPremiseNW = null;
		TimeUtils time = new TimeUtils();
		
		try {
			time.setStartTime();
			RequestPremiseNWList reqPremiseNW = generateRequest();
			
			WMPremiseSDNAPI api = new WMPremiseSDNAPI(GlobalConstants.URL_PREMISE_SDN_API_WM);
			resPremiseNW = api.readNetwork(GlobalConstants.HTTP_GET, reqPremiseNW);
			
			double duration = time.getDuration();
			System.out.println("\nTime for reading a premise network = " + duration + " ms\n");
			
			api.printResponsePremiseNetwork(resPremiseNW);
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
