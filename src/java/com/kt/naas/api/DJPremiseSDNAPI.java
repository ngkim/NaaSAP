package com.kt.naas.api;

import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;

public class DJPremiseSDNAPI extends PremiseSDNAPI {

	public DJPremiseSDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		super(request, response, url);
		// TODO Auto-generated constructor stub
		
		this.setUrlRead(url + "/api.retrievePremiseSDNConnection");
		this.setUrlUpdate(url + "/api.updatePremiseSDNConnection?CpSvcId=CSDN000001");
		this.setUrlDelete(url + "/api.deletePremiseSDNConnection");
	}

}
