package com.kt.naas.api;

import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.util.DebugUtils;
import com.kt.naas.util.RestAPIUtils;

public class SDNAPI {
	protected RequestMessage request;
	protected ResponseMessage response;
	
	protected RestAPIUtils apiUtil;
	
	protected String url;
	protected String urlCreate;
	protected String urlRead;
	protected String urlUpdate;
	protected String urlDelete;

	public SDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		this.request = request;
		this.response = response;
		this.url = url;

		apiUtil = new RestAPIUtils();
	}

	public String getUrlCreate() {
		return urlCreate;
	}

	public void setUrlCreate(String urlCreate) {
		this.urlCreate = url + urlCreate;
	}

	public String getUrlRetrieveNetwork() {
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
	
	public <E> String getRequestXML(E req) {
		String xml = "";

		try {
			xml = apiUtil.getRequestXML(req);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}

		return xml;

	}
	
	public <E> E xmlToResponse(String responseXml, E obj) {
		try {
			obj = apiUtil.getResponseObject(responseXml, obj);
		} catch (Exception e) {
			DebugUtils.sendResponse(response, -1, e.toString());
		}
		return obj;
	}

}
