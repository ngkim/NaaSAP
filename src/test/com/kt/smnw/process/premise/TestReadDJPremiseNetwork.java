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
public class TestReadDJPremiseNetwork {
	@Autowired
	private RequestClient requestClient;
	
	private RequestPremiseNWList generateRequest() {
		RequestPremiseNWList reqPremiseNW = new RequestPremiseNWList();
		
		reqPremiseNW.setTenantName("농협_우면지사");
			
		return reqPremiseNW;		
	}

	@Test
	public void testReadDJPremiseNetwork() {
		ResponsePremiseNWList resPremiseNW = null;
		TimeUtils time = new TimeUtils();
		
		try {
			time.setStartTime();
			RequestPremiseNWList reqPremiseNW = generateRequest();
			
			DJPremiseSDNAPI api = new DJPremiseSDNAPI(GlobalConstants.URL_PREMISE_SDN_API_DJ);
			resPremiseNW = api.readNetwork(reqPremiseNW);
			
			double duration = time.getDuration() / 1000;
			System.err.println("\nTime for reading a premise network = " + duration + " sec\n");
			
			api.printResponsePremiseNetwork(resPremiseNW);
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
