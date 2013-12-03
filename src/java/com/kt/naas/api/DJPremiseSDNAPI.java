package com.kt.naas.api;

import com.kt.naas.GlobalConstants;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.util.NetworkSwapUtils;
import com.kt.naas.util.SnmpUtils;
import com.kt.naas.xml.RequestCreatePremiseNetwork;
import com.kt.naas.xml.RequestDeletePremiseNetwork;
import com.kt.naas.xml.ResponseCreatePremiseNetwork;
import com.kt.naas.xml.ResponseDeletePremiseNetwork;

public class DJPremiseSDNAPI extends PremiseSDNAPI {

	public DJPremiseSDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		super(request, response, url);
		// TODO Auto-generated constructor stub

		this.setUrlRead("/api.retrievePremiseSDNConnection");
		this.setUrlCreate("/api.updatePremiseSDNConnection");
		this.setUrlDelete("/api.deletePremiseSDNConnection");

		this.setSnmpAgent("10.10.65.5");
	}

	public DJPremiseSDNAPI(String url) {
		super(url);
		// TODO Auto-generated constructor stub

		this.setUrlRead("/api.retrievePremiseSDNConnection");
		this.setUrlCreate("/api.updatePremiseSDNConnection");
		this.setUrlDelete("/api.deletePremiseSDNConnection");

		this.setSnmpAgent("10.10.65.5");
	}

	@Override
	public ResponseCreatePremiseNetwork createNetwork(
			RequestCreatePremiseNetwork req, boolean fromApp) {

		if (!GlobalConstants.OP_DEMO_SWAP_DJ && fromApp) {
			System.err.println("*** CreateNetwork - Call DJ VLAN swap...");
			swapNetworkToPOTN();
		}

		return super.createNetwork(req);
	}

	@Override
	public ResponseDeletePremiseNetwork deleteNetwork(
			int method, RequestDeletePremiseNetwork req, boolean fromApp) {

		if (!GlobalConstants.OP_DEMO_SWAP_DJ && fromApp) {
			System.err.println("*** DeleteNetwork - Call DJ VLAN swap...");
			swapNetworkToInternet();
		}

		return super.deleteNetwork(req);
	}

	private boolean swapNetworkToPOTN() {
		boolean result = false;

		if (getSwapMethod() == GlobalConstants.SWAP_METHOD_SNMP) {
			SnmpUtils.vlanSwap(getSnmpAgent(),
					GlobalConstants.DJ_PREMISE_VLAN_ITF_TO_SWAP,
					GlobalConstants.DJ_PREMISE_VLAN_INTERNET);
			result = true;
		} else if (getSwapMethod() == GlobalConstants.SWAP_METHOD_OPENFLOW) {
			NetworkSwapUtils util = new NetworkSwapUtils(
					GlobalConstants.DJ_FLOODLIGHT,
					GlobalConstants.SW_DJ_OPENFLOW);

			util.deleteStaticFlow("INTERNET_INBOUND");
			util.deleteStaticFlow("INTERNET_OUTBOUND");
			util.deleteStaticFlow("DROP_POTN");

			util.addDropFlow("DROP_INTERNET", "9");
			util.addStaticFlow("POTN_INBOUND", "1", "17");
			util.addStaticFlowFromHost("POTN_OUTBOUND", "17", "1",
					"210.183.241.77");

			result = true;
		}
		return result;
	}

	public boolean swapNetworkToInternet() {
		boolean result = false;

		if (getSwapMethod() == GlobalConstants.SWAP_METHOD_SNMP) {
			SnmpUtils.vlanSwap(getSnmpAgent(),
					GlobalConstants.DJ_PREMISE_VLAN_ITF_TO_SWAP,
					GlobalConstants.DJ_PREMISE_VLAN_INTERNET);
			
			result = true;
		} else if (getSwapMethod() == GlobalConstants.SWAP_METHOD_OPENFLOW) {

			NetworkSwapUtils util = new NetworkSwapUtils(
					GlobalConstants.DJ_FLOODLIGHT,
					GlobalConstants.SW_DJ_OPENFLOW);

			util.deleteStaticFlow("POTN_INBOUND");
			util.deleteStaticFlow("POTN_OUTBOUND");
			util.deleteStaticFlow("DROP_INTERNET");

			util.addDropFlow("DROP_POTN", "1");
			util.addStaticFlow("INTERNET_INBOUND", "9", "17");
			util.addStaticFlowFromHost("INTERNET_OUTBOUND", "17", "9",
					"210.183.241.77");

			result = true;
		}
		return result;
	}

	private int getSwapMethod() {
		return GlobalConstants.OP_DJ_SWAP_METHOD;
	}
}
