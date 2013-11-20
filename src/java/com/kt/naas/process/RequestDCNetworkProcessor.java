package com.kt.naas.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kt.naas.GlobalConstants;
import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.util.DebugUtils;
import com.kt.naas.util.RequestClient;
import com.kt.naas.util.RestAPIUtils;
import com.kt.naas.xml.CloudVNID;
import com.kt.naas.xml.CloudVirtualNetwork;
import com.kt.naas.xml.CloudVirtualNetworkList;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.ResponseCloudNWList;

public class RequestDCNetworkProcessor extends RequestProcessor {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private String url = "http://210.183.241.184:5000/RetrievedCloudSDNTenantNEtworkList";
	
	@SuppressWarnings("unused")
	private String wmResponseXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?> <ResponseInfo>   <ReturnCode>200</ReturnCode>   <ReturnCodeDescription>Success</ReturnCodeDescription>   <TenantId>0be2a9fc8a7040d8963c9f67f521bdab</TenantId>   <TenantName>cloudsdn</TenantName>   "
			+ "<VirtualNetworkList>	"
			+ "<VirtualNetwork>	<VirtualNetworkName>우면_CDC망_1</VirtualNetworkName>		<VirtualNetworkID>d2f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>210.183.240.0/24</Subnet>	</VirtualNetwork>   "
			+ "<VirtualNetwork>	<VirtualNetworkName>우면_CDC망_2</VirtualNetworkName>		<VirtualNetworkID>d1f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>210.183.241.0/24</Subnet>	</VirtualNetwork>   "
			+ "<VirtualNetwork>	<VirtualNetworkName>우면_CDC망_3</VirtualNetworkName>		<VirtualNetworkID>d1f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>210.183.242.0/24</Subnet>	</VirtualNetwork>   "
			+ "</VirtualNetworkList> </ResponseInfo>";

	@Autowired
	RequestClient requestClient;

	@Override
	// Receive request from WAS and Send it to API Server
	public void processRequest() {
		String requestXml = "", responseXml = "";
		RestAPIUtils api = new RestAPIUtils();
		
		HttpResponse res = null;
		try {
			RequestInfoCloudSDN req = recvRequestfromWeb();

			requestXml = getRequestXML(req);			
			DebugUtils.printDebugMsg("Request XML...\n" + requestXml);				
			
			// get response from server
			if( GlobalConstants.OP_DEMO_CLOUD ) {
				Thread.sleep(1000);
				responseXml = wmResponseXml;
			} else {
				res = api.requestToAPIServer(url, GlobalConstants.HTTP_POST, requestXml);
				responseXml = api.getResponseXml(res);
			}						
			DebugUtils.printDebugMsg("Response XML...\n" + responseXml);

			// convert response to response object
			ResponseCloudNWList nwList = getResponseObject(responseXml);			
			if (nwList == null) {
				DebugUtils.sendResponse(response, -1, "Error! No NW List...");				
			} else {
				if( GlobalConstants.OP_DEMO_CLOUD ) 
					DebugUtils.printResponseCloudNWList(nwList);
				
				// send response to web
				sendResponseToWeb(nwList);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			DebugUtils.sendResponse(response, -1, e.toString());	
		}
	}

	private void sendResponseToWeb(ResponseCloudNWList nwList) {
		FieldBuffer buf = null;

		try {
			buf = new FieldBuffer();

			// TODO: Remove DCID
			buf.putString("DCID", "123456");
			buf.putString("TENANTNAME", nwList.getTenantname());

			ArrayList<CloudVirtualNetworkList> list = nwList.getVNIDInfo();
			for (int i = 0; i < list.size(); i++) {
				CloudVirtualNetworkList vnl = list.get(i);
				ArrayList<CloudVirtualNetwork> vnlist = vnl.getVnlist();
				for (int j = 0; j < vnlist.size(); j++) {
					CloudVirtualNetwork item = vnlist.get(j);
					buf.putString("NWNAME", item.getName());
					buf.putString("VNID", item.getVNID());
					buf.putString("SUBNET", item.getSubnet());
				}
			}

			// Send response to Web
			response.setFieldBuffer(buf);
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

	}

	public ResponseCloudNWList getResponseObject(String responseXml) {
		ResponseCloudNWList res = null;
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ResponseCloudNWList.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			StringReader reader = new StringReader(responseXml);
			res = (ResponseCloudNWList) jaxbUnmarshaller.unmarshal(reader);

		} catch (Exception e) {
			e.printStackTrace();
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		return res;
	}

	private String getRequestXML(RequestInfoCloudSDN req) {
		String requestXml = "";
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RequestInfoCloudSDN.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter writer = new StringWriter();
			jaxbMarshaller.marshal(req, writer);

			requestXml = writer.toString();
		} catch (JAXBException e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		return requestXml;
	}

	private RequestInfoCloudSDN recvRequestfromWeb() {
		String dcid = null;
		String tenantname = null;
		try {
			FieldBuffer inBuf = request.getFieldBuffer();

			dcid = inBuf.getString("DCID");
			tenantname = inBuf.getString("TENANTNAME");
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		tenantname = "e84ea728cd134dea9110df0c2e9398b0";

		RequestInfoCloudSDN req = null;
		try {
			req = new RequestInfoCloudSDN();
			req.setDcid(dcid);
			req.setTid(tenantname);

		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		return req;
	}
}
