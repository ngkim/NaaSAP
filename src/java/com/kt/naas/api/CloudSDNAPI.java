package com.kt.naas.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;

import com.kt.naas.GlobalConstants;
import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.util.DebugUtils;
import com.kt.naas.xml.CloudConnectionList;
import com.kt.naas.xml.CloudSwitch;
import com.kt.naas.xml.CloudVNID;
import com.kt.naas.xml.CloudVirtualNetwork;
import com.kt.naas.xml.CloudVirtualNetworkList;
import com.kt.naas.xml.RequestCloudNWList;
import com.kt.naas.xml.RequestCreateCloudNetwork;
import com.kt.naas.xml.RequestCreateTransportNetwork;
import com.kt.naas.xml.RequestDeleteCloudNetwork;
import com.kt.naas.xml.RequestInfoCloudSDN;
import com.kt.naas.xml.RequestInfoCloudSDNConnnection;
import com.kt.naas.xml.RequestSwapCloudNetwork;
import com.kt.naas.xml.ResponseCloudNWList;
import com.kt.naas.xml.ResponseCreateCloudNetwork;
import com.kt.naas.xml.ResponseCreateTransportNetwork;
import com.kt.naas.xml.ResponseDeleteCloudNetwork;
import com.kt.naas.xml.ResponseSwapCloudNetwork;

public class CloudSDNAPI extends SDNAPI {

	private String wmResponseXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?> <ResponseInfo>   <ReturnCode>200</ReturnCode>   <ReturnCodeDescription>Success</ReturnCodeDescription>   <TenantId>0be2a9fc8a7040d8963c9f67f521bdab</TenantId>   <TenantName>cloudsdn</TenantName>   "
			+ "<VirtualNetworkList>	"
			+ "<VirtualNetwork>	<VirtualNetworkName>우면_CDC망_1</VirtualNetworkName>		<VirtualNetworkID>d2f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>210.183.240.0/24</Subnet>	</VirtualNetwork>   "
			+ "<VirtualNetwork>	<VirtualNetworkName>우면_CDC망_2</VirtualNetworkName>		<VirtualNetworkID>d1f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>210.183.241.0/24</Subnet>	</VirtualNetwork>   "
			+ "<VirtualNetwork>	<VirtualNetworkName>우면_CDC망_3</VirtualNetworkName>		<VirtualNetworkID>d1f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		<Subnet>210.183.242.0/24</Subnet>	</VirtualNetwork>   "
			+ "</VirtualNetworkList> </ResponseInfo>";

	public CloudSDNAPI(RequestMessage request, ResponseMessage response,
			String apiServer) {
		super(request, response, apiServer);

		this.setUrlRead("/RetrievedCloudSDNTenantNEtworkList");
		this.setUrlCreate("/createCloudSDNConnection");
		this.setUrlSwap("/ConnectKoren");
		this.setUrlDelete("/ConnectInternet");
	}

	public CloudSDNAPI(String apiServer) {
		super(apiServer);

		this.setUrlRead("/RetrievedCloudSDNTenantNEtworkList");
		this.setUrlCreate("/createCloudSDNConnection");
		this.setUrlSwap("/ConnectKoren");
		this.setUrlDelete("/ConnectInternet");
	}

	public ResponseCreateCloudNetwork createNetwork(
			RequestCreateCloudNetwork req) {
		ResponseCreateCloudNetwork resCloudNW = new ResponseCreateCloudNetwork();

		try {
			String responseXml = apiUtil.callAPI(getUrlCreate(),
					GlobalConstants.HTTP_POST, getRequestXML(req));
			resCloudNW = xmlToResponse(responseXml, resCloudNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resCloudNW;
	}

	public ResponseDeleteCloudNetwork deleteNetwork(
			RequestDeleteCloudNetwork req) {
		ResponseDeleteCloudNetwork resCloudNW = new ResponseDeleteCloudNetwork();

		try {
			String responseXml = apiUtil.callAPI(getUrlDelete(),
					GlobalConstants.HTTP_POST, getRequestXML(req));
			resCloudNW = xmlToResponse(responseXml, resCloudNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resCloudNW;
	}

	public ResponseCloudNWList readNetwork(RequestInfoCloudSDN req) {
		ResponseCloudNWList resCloudNW = new ResponseCloudNWList();

		try {
			String responseXml = apiUtil.callAPI(getUrlRead(),
					GlobalConstants.HTTP_POST, getRequestXML(req));
			resCloudNW = xmlToResponse(responseXml, resCloudNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resCloudNW;
	}

	public ResponseSwapCloudNetwork swapNetwork(RequestSwapCloudNetwork req) {
		return swapNetwork(GlobalConstants.HTTP_POST, req);
	}

	public ResponseSwapCloudNetwork swapNetwork(int method,
			RequestSwapCloudNetwork req) {
		ResponseSwapCloudNetwork resCloudNW = new ResponseSwapCloudNetwork();

		try {
			String responseXml = apiUtil.callAPI(this.getUrlSwap(), method,
					getRequestXML(req));
			resCloudNW = xmlToResponse(responseXml, resCloudNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resCloudNW;
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
		tenantname = "b999ba92afa2456287f7fd1a8b07e755";

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

	public void sendResponseToWeb(RequestInfoCloudSDN req,
			ResponseCloudNWList nwList) {
		FieldBuffer buf = null;

		try {
			buf = new FieldBuffer();

			buf.putString("DCID", req.getDcid());
			buf.putString("TENANTNAME", nwList.getTenantname());
			buf.putString("TENANTID", nwList.getTenantid());

			ArrayList<CloudVirtualNetwork> list = nwList.getVNIDInfo();
			for (int i = 0; i < list.size(); i++) {
				CloudVirtualNetwork item = list.get(i);

				buf.putString("NWNAME", item.getName());
				buf.putString("VNID", item.getVNID());
				buf.putString("SUBNET", item.getSubnet());
			}

			// Send response to Web
			response.setFieldBuffer(buf);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

	}

	// request and get response from API server
	public String getResponseXML(String apiUrl, String requestXml) {
		String xml = "";

		try {
			if (GlobalConstants.OP_DEMO_CLOUD) {
				Thread.sleep(1000);
				xml = wmResponseXml;
			} else {
				xml = apiUtil.callAPI(getUrlCreate(),
						GlobalConstants.HTTP_POST, requestXml);
			}
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return xml;
	}

	public void printResponseCreateCloudNetwork(ResponseCreateCloudNetwork res) {
		try {
			printUtil.printKeyAndValue("RETURN CODE", res.getReturnCode());
			printUtil.printKeyAndValue("RETURN MSG", res.getMessage());

			printUtil.printKeyAndValue("DCSVCID", res.getDcsvcid());
			printUtil.printKeyAndValue("TENANTNAME", res.getTenantname());
			printUtil.printKeyAndValue("TENANTID", res.getTenantid());

			ArrayList<CloudVNID> list = res.getVNIDInfo();
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					CloudVNID item = list.get(i);
					printUtil.printKeyAndValue("\tNWNAME", item.getName());
					printUtil.printKeyAndValue("\tVNID", item.getVNID());
					printUtil.printKeyAndValue("\tSUBNET", item.getSubnet());
					printUtil.printKeyAndValue("\tBW", item.getBandwidth());

					CloudConnectionList conList = item.getConnectionList();
					if (conList != null) {
						ArrayList<CloudSwitch> swlist = conList
								.getConnectionInfo();
						if (swlist != null) {
							for (int j = 0; j < swlist.size(); j++) {
								CloudSwitch sw = swlist.get(j);
								printUtil.printKeyAndValue("\t\tSWNAME",
										sw.getName());
								printUtil.printKeyAndValue("\t\tSWID",
										sw.getDpid());
								printUtil.printKeyAndValue("\t\tSWType",
										sw.getType());
								printUtil.printKeyAndValue("\t\tUpPort",
										sw.getUplinkport());
								printUtil.printKeyAndValue("\t\tDownPort",
										sw.getDownlinkport());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printResponseCloudNWList(ResponseCloudNWList nwList) {
		System.out.println("RETURN CODE= " + nwList.getReturnCode());
		System.out.println("RETURN MSG= " + nwList.getMessage());
		System.out.println("TENANTNAME= " + nwList.getTenantname());
		System.out.println("TENANTID= " + nwList.getTenantid());

		ArrayList<CloudVirtualNetwork> list = nwList.getVNIDInfo();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				CloudVirtualNetwork item = list.get(i);
				System.out.println("NWNAME= " + item.getName());
				System.out.println("VNID= " + item.getVNID());
				System.out.println("SUBNET= " + item.getSubnet());
			}
		}
	}
}
