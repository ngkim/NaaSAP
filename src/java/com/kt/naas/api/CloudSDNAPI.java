package com.kt.naas.api;

import java.util.ArrayList;

import org.apache.http.HttpResponse;

import com.kt.naas.GlobalConstants;
import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.util.DebugUtils;
import com.kt.naas.xml.CloudVirtualNetwork;
import com.kt.naas.xml.CloudVirtualNetworkList;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.ResponseCloudNWList;

public class CloudSDNAPI extends SDNAPI {
	
	private String wmResponseXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?> <ResponseInfo>   <ReturnCode>200</ReturnCode>   <ReturnCodeDescription>Success</ReturnCodeDescription>   <TenantId>0be2a9fc8a7040d8963c9f67f521bdab</TenantId>   <TenantName>cloudsdn</TenantName>   "
			+ "<VirtualNetworkList>	"
			+ "<VirtualNetwork>	<VirtualNetworkName>우면_CDC망_1</VirtualNetworkName>		<VirtualNetworkID>d2f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>210.183.240.0/24</Subnet>	</VirtualNetwork>   "
			+ "<VirtualNetwork>	<VirtualNetworkName>우면_CDC망_2</VirtualNetworkName>		<VirtualNetworkID>d1f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>210.183.241.0/24</Subnet>	</VirtualNetwork>   "
			+ "<VirtualNetwork>	<VirtualNetworkName>우면_CDC망_3</VirtualNetworkName>		<VirtualNetworkID>d1f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>210.183.242.0/24</Subnet>	</VirtualNetwork>   "
			+ "</VirtualNetworkList> </ResponseInfo>";

	public CloudSDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		super(request, response, url);
	}

	public RequestInfoCloudSDN recvRequestfromWeb() {
		String dcid = null;
		String tenantname = null;
		try {
			FieldBuffer inBuf = request.getFieldBuffer();

			dcid = inBuf.getString("DCID");
			tenantname = inBuf.getString("TENANTNAME");
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		// TODO: remove this line
		tenantname = "e84ea728cd134dea9110df0c2e9398b0";

		RequestInfoCloudSDN req = null;
		try {
			req = new RequestInfoCloudSDN();
			req.setDcid(dcid);
			req.setTid(tenantname);

		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return req;
	}

	public void sendResponseToWeb(ResponseCloudNWList nwList) {
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
			DebugUtils.sendResponse(response, -1, e.toString());
		}

	}

	public String getRequestXML(RequestInfoCloudSDN req) {
		String xml = "";

		try {
			xml = apiUtil.getRequestXML(req);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return xml;

	}

	// get response from API server
	public String getResponseXML(String requestXml) {
		String xml = "";

		HttpResponse res = null;
		try {
			if (GlobalConstants.OP_DEMO_CLOUD) {
				Thread.sleep(1000);
				xml = wmResponseXml;
			} else {
				res = apiUtil.requestToAPIServer(url,
						GlobalConstants.HTTP_POST, requestXml);
				xml = apiUtil.getResponseXml(res);
			}
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return xml;

	}

	public ResponseCloudNWList xmlToResponse(String responseXml) {
		ResponseCloudNWList nwList = null;
		try {
			nwList = apiUtil.getResponseObject(responseXml, new ResponseCloudNWList());
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}
		return nwList;
	}

}