package com.kt.naas.util;

import com.kt.naas.domain.OpenstackUri;

public class ConfigUri {

	public static String makeUri(String objectType, Object message) {
		String url = "";
		
		if (message == null) return null;
		
		OpenstackUri openstackUri = (OpenstackUri)message;
		
		url = openstackUri.getProtocol();
		url = url + "://" + openstackUri.getTargetIp();
		url = url + ":" + openstackUri.getPort();
		
		//if(!openstackUri.getVersion().equals(null))
			url = url + "/" + openstackUri.getVersion();

		//if(!openstackUri.getTenantId().equals(null))
			url = url + "/" + openstackUri.getTenantId();
		
		url = url + "/" + openstackUri.getAction();
		
		if(objectType.equals("DEL_VM"))
			url = url + "/" + openstackUri.getId();		
		
		return url;
	}

}
