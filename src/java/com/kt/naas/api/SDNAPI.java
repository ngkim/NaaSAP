package com.kt.naas.api;

import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.util.RestAPIUtils;

public class SDNAPI {
	protected RequestMessage request;
	protected ResponseMessage response;
	
	protected RestAPIUtils apiUtil;
	
	protected String url;
	protected String urlCreateNetwork;
	protected String urlRetrieveNetwork;

	public SDNAPI(RequestMessage request, ResponseMessage response,
			String url) {
		this.request = request;
		this.response = response;
		this.url = url;

		apiUtil = new RestAPIUtils();
	}

	public String getUrlCreateNetwork() {
		return urlCreateNetwork;
	}

	public void setUrlCreateNetwork(String urlCreateNetwork) {
		this.urlCreateNetwork = urlCreateNetwork;
	}

	public String getUrlRetrieveNetwork() {
		return urlRetrieveNetwork;
	}

	public void setUrlRetrieveNetwork(String urlRetrieveNetwork) {
		this.urlRetrieveNetwork = urlRetrieveNetwork;
	}

}
