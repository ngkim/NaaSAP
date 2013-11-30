package com.kt.smnw.process.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.CloudSDNAPI;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.util.DebugUtils;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.TimeUtils;
import com.kt.naas.xml.RequestCloudNWList;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.RequestInfoEthernet;
import com.kt.naas.xml.ResponseCloudNWList;
import com.kt.naas.xml.ResponseInfoEthernet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestReadCloudNetwork {
	@Autowired
	private RequestClient requestClient;
	
	public RequestInfoCloudSDN generateRequest() {
		RequestInfoCloudSDN reqCloudNW = new RequestInfoCloudSDN();
//		RequestCloudNWList reqCloudNW = new RequestCloudNWList();
		
		reqCloudNW.setDcid("dn_0001");
//		reqCloudNW.setTenantName("e84ea728cd134dea9110df0c2e9398b0");
		reqCloudNW.setTid("b999ba92afa2456287f7fd1a8b07e755");
		
		return reqCloudNW;
	}

	@Test
	public void testReadCloudNetwork() {
		ResponseCloudNWList res = new ResponseCloudNWList();
		
		TimeUtils time = new TimeUtils();
		try {
			time.setStartTime();
			
			RequestInfoCloudSDN req = generateRequest();	
		
			CloudSDNAPI api = new CloudSDNAPI(GlobalConstants.URL_CLOUD_SDN_API);
			res = api.readNetwork(req);
			
			double duration = time.getDuration();			
			System.err.println("\nTime for reading a cloud network = " + duration + " ms\n");
			
			api.printResponseCloudNWList(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
