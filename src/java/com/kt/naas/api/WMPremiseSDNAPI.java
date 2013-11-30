package com.kt.naas.api;

import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;

public class WMPremiseSDNAPI extends PremiseSDNAPI {

	public WMPremiseSDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		super(request, response, url);

		this.setUrlRead("/api.retrievePremiseSDNConnection");
		this.setUrlCreate("/api.updatePremiseSDNConnection");
		this.setUrlDelete("/api.deletePremiseSDNConnection");
	}

	public WMPremiseSDNAPI(String url) {
		super(url);

		this.setUrlRead("/api.retrievePremiseSDNConnection");
		this.setUrlCreate("/api.updatePremiseSDNConnection");
		this.setUrlDelete("/api.deletePremiseSDNConnection");
	}
}
