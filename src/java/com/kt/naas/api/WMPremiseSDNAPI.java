package com.kt.naas.api;

import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;

public class WMPremiseSDNAPI extends PremiseSDNAPI {

	public WMPremiseSDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		super(request, response, url);

		this.setUrlRead("/retrievePremiseSDNConnection?tenant_name=NH_ADMIN");
		this.setUrlCreate("/updatepremisesdnvlan");
		this.setUrlDelete("/deletepremisesdnvlan");
	}

	public WMPremiseSDNAPI(String url) {
		super(url);

		this.setUrlRead("/retrievePremiseSDNConnection?tenant_name=NH_ADMIN");
		this.setUrlCreate("/updatepremisesdnvlan");
		this.setUrlDelete("/deletepremisesdnvlan");
	}
}
