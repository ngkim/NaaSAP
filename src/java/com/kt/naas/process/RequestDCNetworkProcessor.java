package com.kt.naas.process;

import org.springframework.beans.factory.annotation.Autowired;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.CloudSDNAPI;
import com.kt.naas.util.DebugUtils;
import com.kt.naas.util.RequestClient;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.ResponseCloudNWList;

public class RequestDCNetworkProcessor extends RequestProcessor {
	@Autowired
	RequestClient requestClient;

	@Override
	// Receive request from WAS and Send it to API Server
	public void processRequest() {
		CloudSDNAPI api = new CloudSDNAPI(request, response, GlobalConstants.URL_CLOUD_SDN_API);		
		try {
			RequestInfoCloudSDN req = api.recvRequestfromWeb();
			ResponseCloudNWList nwList = api.readNetwork(req);
			
			if (nwList == null) {
				DebugUtils.sendResponse(response, -1, "Error! No NW List...");				
			} else {
				if( GlobalConstants.OP_DEMO_CLOUD ) api.printResponseCloudNWList(nwList);
				
				// send response to web
				api.sendResponseToWeb(req, nwList);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			DebugUtils.sendResponse(response, -1, e.toString());	
		}
	}	
}
