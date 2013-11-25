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
import com.kt.naas.xml.RequestPremiseNWList;
import com.kt.naas.xml.ResponseCreatePremiseNetwork;
import com.kt.naas.xml.ResponseCreateTransportNetwork;
import com.kt.naas.xml.ResponsePremiseNWList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestCreateWMPremiseNetwork {
	@Autowired
	private RequestClient requestClient;
	
	private RequestCreatePremiseNetwork generateRequest() {
		RequestCreatePremiseNetwork reqPremiseNW = new RequestCreatePremiseNetwork();
		
		reqPremiseNW.setTenantName("NH_ADMIN");
		reqPremiseNW.setNetworkname("NH_PrivateNW1");
		reqPremiseNW.setBandwidth(1000 * 1000);
			
		return reqPremiseNW;		
	}

	@Test
	public void testCreateWMPremiseNetwork() {
		ResponseCreatePremiseNetwork resPremiseNW = null;
		TimeUtils time = new TimeUtils();
		
		try {
			time.setStartTime();
			RequestCreatePremiseNetwork reqPremiseNW = generateRequest();
			
			WMPremiseSDNAPI api = new WMPremiseSDNAPI(GlobalConstants.URL_PREMISE_SDN_API_WM);
			resPremiseNW = api.createNetwork(reqPremiseNW);
			
			double duration = time.getDuration();
			System.out.println("Time for reading a premise network = " + duration + " ms");
			
			api.printResponseCreatePremiseNetwork(resPremiseNW);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
