package com.kt.naas.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.kt.naas.GlobalConstants;
import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.util.DebugUtils;
import com.kt.naas.util.SnmpUtils;
import com.kt.naas.xml.PremiseNetwork;
import com.kt.naas.xml.PremiseSwitch;
import com.kt.naas.xml.PremiseSwitchList;
import com.kt.naas.xml.RequestCreatePremiseNetwork;
import com.kt.naas.xml.RequestDeletePremiseNetwork;
import com.kt.naas.xml.RequestPremiseNWList;
import com.kt.naas.xml.ResponseCreatePremiseNetwork;
import com.kt.naas.xml.ResponseDeletePremiseNetwork;
import com.kt.naas.xml.ResponsePremiseNWList;

public class PremiseSDNAPI extends SDNAPI {

	private String djPremiseResponseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseInfo><ReturnCode>200</ReturnCode><ReturnCodeDescription>Success</ReturnCodeDescription><CpSvcId>PSDN000001</CpSvcId><TenantId>A111222333</TenantId><TenantName>NH_ADMIN</TenantName>"
			+ "<NetworkList><NetworkName>농협_전민지사_사내망_1</NetworkName><Subnet>221.145.180.0/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>jm_endpoint_sw_01</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>jm_l2_sw_01</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>jm_aggr_sw_01</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList>"
			+ "<NetworkList><NetworkName>농협_전민지사_사내망_2</NetworkName><Subnet>221.145.200.0/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>jm_endpoint_sw_02</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>jm_l2_sw_02</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>jm_aggr_sw_02</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList>"
			+ "</ResponseInfo>";
	private String wmPremiseResponseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseInfo><ReturnCode>200</ReturnCode><ReturnCodeDescription>Success</ReturnCodeDescription><CpSvcId>PSDN000002</CpSvcId><TenantId>A111222333</TenantId><TenantName>NH_ADMIN</TenantName>"
			+ "<NetworkList><NetworkName>농협_우면지사_사내망_1</NetworkName><Subnet>210.183.240.0/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>wm_endpoint_sw_01</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>wm_l2_sw_01</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>wm_aggr_sw_01</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList>"
			+ "<NetworkList><NetworkName>농협_우면지사_사내망_2</NetworkName><Subnet>210.183.241.0/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>wm_endpoint_sw_02</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>wm_l2_sw_02</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>wm_aggr_sw_02</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList>"
			+ "<NetworkList><NetworkName>농협_우면지사_사내망_3</NetworkName><Subnet>210.183.242.0/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>wm_endpoint_sw_03</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>wm_l2_sw_03</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>wm_aggr_sw_03</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList>"
			+ "</ResponseInfo>";
	
	private String snmpAgent;

	public PremiseSDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		super(request, response, url);

		this.setUrlRead("/api.retrievePremiseSDNConnection");
		this.setUrlCreate("/api.updatePremiseSDNConnection");
		this.setUrlDelete("/api.deletePremiseSDNConnection");
	}

	public PremiseSDNAPI(String url) {
		super(url);

		this.setUrlRead("/api.retrievePremiseSDNConnection");
		this.setUrlCreate("/api.updatePremiseSDNConnection");
		this.setUrlDelete("/api.deletePremiseSDNConnection");
	}
	
	public ResponseCreatePremiseNetwork createNetwork(
			RequestCreatePremiseNetwork req) {
		return createNetwork(GlobalConstants.HTTP_POST, req);
	}

	public ResponseCreatePremiseNetwork createNetwork(int method,
			RequestCreatePremiseNetwork req) {
		ResponseCreatePremiseNetwork resPremiseNW = new ResponseCreatePremiseNetwork();

		try {
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("URLCreate", getUrlCreate());

			String responseXml = apiUtil.callAPI(getUrlCreate(), method,
					getRequestXML(req));
			
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("ResponseXML", responseXml);

			resPremiseNW = xmlToResponse(responseXml, resPremiseNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resPremiseNW;
	}

	public ResponsePremiseNWList readNetwork(RequestPremiseNWList req) {
		return readNetwork(GlobalConstants.HTTP_POST, req);
	}

	public ResponsePremiseNWList readNetwork(int method,
			RequestPremiseNWList reqPremiseNW) {
		ResponsePremiseNWList resPremiseNW = new ResponsePremiseNWList();
		try {
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("URLRead", getUrlRead());

			String responseXml = apiUtil.callAPI(getUrlRead(), method,
					getRequestXML(reqPremiseNW));
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("ResponseXML", responseXml);

			resPremiseNW = xmlToResponse(responseXml, resPremiseNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resPremiseNW;
	}

	public ResponseDeletePremiseNetwork deleteNetwork(
			RequestDeletePremiseNetwork req) {
		return deleteNetwork(GlobalConstants.HTTP_POST, req);
	}

	public ResponseDeletePremiseNetwork deleteNetwork(int method,
			RequestDeletePremiseNetwork req) {
		ResponseDeletePremiseNetwork resPremiseNW = new ResponseDeletePremiseNetwork();

		try {
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("URLDelete", getUrlDelete());

			String responseXml = apiUtil.callAPI(getUrlDelete(), method,
					getRequestXML(req));
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("ResponseXML", responseXml);

			resPremiseNW = xmlToResponse(responseXml, resPremiseNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resPremiseNW;
	}

	// Get Messages and Create Request
	public RequestPremiseNWList recvRequestfromWeb() {
		String nmsId = "";
		String tenantname = "";

		RequestPremiseNWList req = null;
		try {
			FieldBuffer inBuf = request.getFieldBuffer();

			tenantname = inBuf.getString("TENANTNAME");
			nmsId = inBuf.getString("NMSID");

			req = new RequestPremiseNWList();
			req.setTenantName(tenantname);
		} catch (Exception e) {
			String retMsg = "" + e;
			response.setResultCode(-1);
			response.setResultMessage(retMsg);
		}

		return req;
	}

	public void sendResponseToWeb(ResponsePremiseNWList res) {
		FieldBuffer buf = null;

		try {
			buf = new FieldBuffer();

			buf.putString("TENANTNAME", res.getTenantname());

			ArrayList<PremiseNetwork> pnList = res.getVnidlist();
			if (pnList != null) {
				for (int i = 0; i < pnList.size(); i++) {
					PremiseNetwork pn = pnList.get(i);

					buf.putString("NWNAME", pn.getName());
					buf.putString("SUBNET", pn.getSubnet());
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

	public void printResponseCreatePremiseNetwork(
			ResponseCreatePremiseNetwork res) {
		printUtil.printKeyAndValue("ReturnCode", res.getReturnCode());
		printUtil.printKeyAndValue("ReturnCodeDescription", res.getMessage());
	}

	public void printResponseDeletePremiseNetwork(
			ResponseDeletePremiseNetwork res) {
		printUtil.printKeyAndValue("ReturnCode", res.getReturnCode());
		printUtil.printKeyAndValue("ReturnCodeDescription", res.getMessage());
	}

	public void printResponsePremiseNetwork(ResponsePremiseNWList res) {
		printUtil.printKeyAndValue("ReturnCode", res.getReturnCode());
		printUtil.printKeyAndValue("ReturnCodeDescription", res.getMessage());

		printUtil.printKeyAndValue("CpSvcId", res.getCpsvcid());
		printUtil.printKeyAndValue("TenantId", res.getTenantid());
		printUtil.printKeyAndValue("TenantName", res.getTenantname());
		printUtil.printKeyAndValue("NeossID", res.getNeossId());
		System.out.println();

		ArrayList<PremiseNetwork> pnList = res.getVnidlist();
		if (pnList != null) {
			for (int i = 0; i < pnList.size(); i++) {
				PremiseNetwork pn = pnList.get(i);
				ArrayList<PremiseSwitchList> psList = pn.getConnectionList();

				printUtil.printKeyAndValue("NetworkName", pn.getName());
				printUtil.printKeyAndValue("Subnet", pn.getSubnet());
				printUtil.printKeyAndValue("VLANID", pn.getVlanid());
				printUtil.printKeyAndValue("Bandwidth", pn.getBandwidth());
				System.out.println();

				if (psList != null) {
					for (int j = 0; j < psList.size(); j++) {
						PremiseSwitchList psl = psList.get(j);
						ArrayList<PremiseSwitch> pSwitch = psl.getSw();
						if (pSwitch != null) {
							for (int k = 0; k < pSwitch.size(); k++) {
								PremiseSwitch sw = pSwitch.get(k);

								printUtil.printKeyAndValue("SWName",
										sw.getName());
								printUtil.printKeyAndValue("SWType",
										sw.getType());
								printUtil
										.printKeyAndValue("SWID", sw.getSwid());
								printUtil.printKeyAndValue("IP", sw.getIp());
								printUtil.printKeyAndValue("UpPort",
										sw.getUpport());
								printUtil.printKeyAndValue("DownPort",
										sw.getDownport());
								System.out.println();
							}
						}
					}
				}
			}
		}
	}

	public String getSnmpAgent() {
		return snmpAgent;
	}

	public void setSnmpAgent(String snmpAgent) {
		this.snmpAgent = snmpAgent;
	}

}
