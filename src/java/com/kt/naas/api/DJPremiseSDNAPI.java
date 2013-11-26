package com.kt.naas.api;

import com.kt.naas.GlobalConstants;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
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
			RequestCreatePremiseNetwork req) {
		
		if(!GlobalConstants.OP_DEMO_SNMP_DJ) {
			SnmpUtils.vlanSwap(getSnmpAgent(), "10020", 21);
			SnmpUtils.vlanSwap(getSnmpAgent(), "10001", 11);
		}
		
		return super.createNetwork(req);
	}

	@Override
	public ResponseDeletePremiseNetwork deleteNetwork(
			RequestDeletePremiseNetwork req) {
		
		if(!GlobalConstants.OP_DEMO_SNMP_DJ) {
		SnmpUtils.vlanSwap(getSnmpAgent(), "10001", 21);
		SnmpUtils.vlanSwap(getSnmpAgent(), "10020", 11);
	}
		return super.deleteNetwork(req);
	}	
	
	
}
