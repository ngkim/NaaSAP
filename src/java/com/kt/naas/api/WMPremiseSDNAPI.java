package com.kt.naas.api;

import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;

public class WMPremiseSDNAPI extends PremiseSDNAPI {

	public WMPremiseSDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		super(request, response, url);
		// TODO Auto-generated constructor stub
		
		this.setUrlRead(url + "/retrievePremiseSDNConnection?tenant_name=NH_ADMIN");
		this.setUrlUpdate(url + "/updatepremisesdnvlan");
		this.setUrlDelete(url + "/deletepremisesdnvlan");
	}

}
