package com.kt.naas.api;

import java.util.ArrayList;

import com.kt.naas.GlobalConstants;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.util.DebugUtils;
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

	public PremiseSDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		super(request, response, url);
	}

	public PremiseSDNAPI(String url) {
		super(url);
	}
	
	public ResponseCreatePremiseNetwork createNetwork(RequestCreatePremiseNetwork req) {
		return createNetwork(GlobalConstants.HTTP_POST, req);
	}
	
	public ResponseCreatePremiseNetwork createNetwork( int method, RequestCreatePremiseNetwork req) {
		ResponseCreatePremiseNetwork resPremiseNW = new ResponseCreatePremiseNetwork();

		try {
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("URLCreate", getUrlCreate());

			String responseXml = apiUtil.callAPI(getUrlCreate(),
					method, getRequestXML(req));
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
	
	public ResponsePremiseNWList readNetwork(int method, RequestPremiseNWList reqPremiseNW) {
		ResponsePremiseNWList resPremiseNW = new ResponsePremiseNWList();
		try {
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("URLRead", getUrlRead());

			String responseXml = apiUtil.callAPI(getUrlRead(),
					method, getRequestXML(reqPremiseNW));
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("ResponseXML", responseXml);

			resPremiseNW = xmlToResponse(responseXml, resPremiseNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resPremiseNW;
	}
	
	public ResponseDeletePremiseNetwork deleteNetwork(RequestDeletePremiseNetwork req) {
		return deleteNetwork(GlobalConstants.HTTP_POST, req);
	}
	
	public ResponseDeletePremiseNetwork deleteNetwork(
			int method, RequestDeletePremiseNetwork req) {
		ResponseDeletePremiseNetwork resPremiseNW = new ResponseDeletePremiseNetwork();

		try {
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("URLDelete", getUrlDelete());

			String responseXml = apiUtil.callAPI(getUrlDelete(),
					method, getRequestXML(req));
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("ResponseXML", responseXml);

			resPremiseNW = xmlToResponse(responseXml, resPremiseNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return resPremiseNW;
	}
	
	public void printResponseCreatePremiseNetwork(ResponseCreatePremiseNetwork res) {
		printUtil.printKeyAndValue("ReturnCode", res.getReturnCode());
		printUtil.printKeyAndValue("ReturnCodeDescription", res.getMessage());
	}
	
	public void printResponseDeletePremiseNetwork(ResponseDeletePremiseNetwork res) {
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

}
