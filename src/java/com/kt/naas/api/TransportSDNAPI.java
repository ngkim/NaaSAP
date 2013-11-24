package com.kt.naas.api;

import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;

import com.kt.naas.xml.RequestCreateTransportNetwork;
import com.kt.naas.xml.RequestDeleteTransportNetwork;
import com.kt.naas.xml.ResponseCreateTransportNetwork;
import com.kt.naas.xml.ResponseDeleteTransportNetwork;

import com.kt.naas.util.DebugUtils;

public class TransportSDNAPI extends SDNAPI {

	public TransportSDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		super(request, response, url);
		// TODO Auto-generated constructor stub
		
		this.setUrlRead(url + "/createEthernet");
		this.setUrlCreate(url + "/deleteEthernet");		
	}
	
	public ResponseCreateTransportNetwork createNetwork(RequestCreateTransportNetwork req) {
		ResponseCreateTransportNetwork resTransportNW = new ResponseCreateTransportNetwork();
	
		try {
			String responseXml = apiUtil.requestToAPIServerHttps(getUrlCreate(), getRequestXML(req));
			resTransportNW = xmlToResponse(responseXml, resTransportNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}
		
		return resTransportNW;		
	}
	
	public ResponseDeleteTransportNetwork deleteNetwork(RequestDeleteTransportNetwork req) {
		ResponseDeleteTransportNetwork resTransportNW = new ResponseDeleteTransportNetwork();
		
		try {
			String responseXml = apiUtil.requestToAPIServerHttps(getUrlDelete(), getRequestXML(req));
			resTransportNW = xmlToResponse(responseXml, resTransportNW);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}
		
		return resTransportNW;	
	}
	
	
	
	

}
