package com.kt.naas.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kt.naas.GlobalConstants;
import com.kt.naas.api.CloudSDNAPI;
import com.kt.naas.util.DebugUtils;
import com.kt.naas.util.RequestClient;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.ResponseCloudNWList;

public class RequestDCNetworkProcessor extends RequestProcessor {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	RequestClient requestClient;

	@Override
	// Receive request from WAS and Send it to API Server
	public void processRequest() {
		String url = "";
		String requestXml = "", responseXml = "";
		
		url = GlobalConstants.URL_CLOUD_SDN_API;
		CloudSDNAPI api = new CloudSDNAPI(request, response, url);		
		try {
			RequestInfoCloudSDN req = api.recvRequestfromWeb();
			
			requestXml = api.getRequestXML(req);
			DebugUtils.printDebugMsg("Request XML...\n" + requestXml);				
			
			responseXml = api.getResponseXML(api.getUrlRetrieveNetwork(), requestXml);
			DebugUtils.printDebugMsg("Response XML...\n" + responseXml);

			ResponseCloudNWList nwList = new ResponseCloudNWList();
			nwList = api.xmlToResponse(responseXml, nwList); 
			if (nwList == null) {
				DebugUtils.sendResponse(response, -1, "Error! No NW List...");				
			} else {
				if( GlobalConstants.OP_DEMO_CLOUD ) DebugUtils.printResponseCloudNWList(nwList);
				
				// send response to web
				api.sendResponseToWeb(nwList);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			DebugUtils.sendResponse(response, -1, e.toString());	
		}
	}	
}
