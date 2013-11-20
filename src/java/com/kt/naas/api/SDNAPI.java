package com.kt.naas.api;

import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.util.RestAPIUtils;

public class SDNAPI {
	protected RequestMessage request;
	protected ResponseMessage response;
	
	protected String url;
	protected RestAPIUtils apiUtil;

	public SDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		this.request = request;
		this.response = response;
		this.url = url;

		apiUtil = new RestAPIUtils();
	}

}
