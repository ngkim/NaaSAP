package com.kt.smnw.process;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.CloudSDNAPI;
import com.kt.naas.api.TransportSDNAPI;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.TimeUtils;
import com.kt.naas.xml.RequestCloudNWList;
import com.kt.naas.xml.RequestCreateCloudNetwork;
import com.kt.naas.xml.RequestDeleteCloudNetwork;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.RequestInfoEthernet;
import com.kt.naas.xml.ResponseCloudNWList;
import com.kt.naas.xml.ResponseCreateCloudNetwork;
import com.kt.naas.xml.ResponseDeleteCloudNetwork;
import com.kt.naas.xml.ResponseInfoEthernet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class TestDeleteCloudNetwork {
	@Autowired
	private RequestClient requestClient;
	
	public RequestDeleteCloudNetwork generateRequest() {
		RequestDeleteCloudNetwork reqCloudNW = new RequestDeleteCloudNetwork();
		
		return reqCloudNW;
	}

	@Test
	public void testCreateCloudNetwork() {
		ResponseDeleteCloudNetwork res = new ResponseDeleteCloudNetwork();
		
		TimeUtils time = new TimeUtils();
		try {
			time.setStartTime();
			
			RequestDeleteCloudNetwork req = generateRequest();	
		
			CloudSDNAPI api = new CloudSDNAPI(GlobalConstants.URL_CLOUD_SDN_API);
			res = api.deleteNetwork(req);
			
			double duration = time.getDuration() / 1000;			
			System.err.println("\nTime for deleting a cloud network = " + duration + " seconds.\n");
			
//			api.printResponseInfoEthernet(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
