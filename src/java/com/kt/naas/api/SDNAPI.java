package com.kt.naas.api;

import com.kt.naas.GlobalConstants;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.util.DebugUtils;
import com.kt.naas.util.PrintUtils;
import com.kt.naas.util.RestAPIUtils;

import javax.xml.bind.UnmarshalException;

public class SDNAPI {
	protected RequestMessage request;
	protected ResponseMessage response;
	
	protected PrintUtils printUtil; 
	protected RestAPIUtils apiUtil;
	
	protected String url;
	protected String urlCreate;
	protected String urlRead;
	protected String urlUpdate;
	protected String urlSwap;
	protected String urlDelete;

	public SDNAPI(String url) {
		this.url = url;

		apiUtil = new RestAPIUtils();
		if(GlobalConstants.OP_DEBUG) printUtil = new PrintUtils();
	}
	
	public SDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		this.request = request;
		this.response = response;
		this.url = url;

		apiUtil = new RestAPIUtils();
		if(GlobalConstants.OP_DEBUG) printUtil = new PrintUtils();
	}

	public String getUrlCreate() {
		return urlCreate;
	}

	public void setUrlCreate(String urlCreate) {
		this.urlCreate = url + urlCreate;
	}

	public String getUrlRead() {
		return urlRead;
	}

	public void setUrlRead(String urlRead) {
		this.urlRead = url + urlRead;
	}

	public String getUrlUpdate() {
		return urlUpdate;
	}

	public void setUrlUpdate(String urlUpdate) {
		this.urlUpdate = url + urlUpdate;
	}

	public String getUrlDelete() {
		return urlDelete;
	}

	public void setUrlDelete(String urlDelete) {
		this.urlDelete = url + urlDelete;
	}
	
	public String getUrlSwap() {
		return urlSwap;
	}

	public void setUrlSwap(String urlSwap) {
		this.urlSwap = url + urlSwap;
	}

	public <E> String getRequestXML(E req) {
		String xml = "";

		try {
			xml = apiUtil.getRequestXML(req);
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("RequestXML", xml);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return xml;

	}
	
	public <E> E xmlToResponse(String responseXml, E obj) throws Exception{
		try {
			if (GlobalConstants.OP_DEBUG)
				printUtil.printKeyAndValue("ResponseXML", responseXml);
			obj = apiUtil.getResponseObject(responseXml, obj);
		} catch (UnmarshalException e) {
			throw new UnmarshalException("Unable to unmarshal: " + e.getMessage()); 
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		return obj;
	}

}
