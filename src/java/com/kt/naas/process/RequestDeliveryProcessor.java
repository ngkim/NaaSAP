package com.kt.naas.process;

import java.util.ArrayList;
import java.util.List;

import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.util.SRUtils;
import com.kt.naas.util.StringUtils;

public class RequestDeliveryProcessor extends RequestProcessor {
	private String serviceRouterIp;
	private String exceptionNetworkPrefix;
	private String exceptionNetworkGateway;
	private String exceptionNetworkPrefix2;
	private String exceptionNetworkGateway2;
	private List<String> exceptionNetworks = new ArrayList<String>();
	private List<String> exceptionNetworks2 = new ArrayList<String>();
	
	public String getServiceRouterIp() {
		return serviceRouterIp;
	}
	public void setServiceRouterIp(String serviceRouterIp) {
		this.serviceRouterIp = serviceRouterIp;
	}
	public String getExceptionNetworkPrefix() {
		return exceptionNetworkPrefix;
	}
	
	// IP 대역 1
	public void setExceptionNetworkPrefix(String exceptionNetworkPrefix) {
		this.exceptionNetworkPrefix = exceptionNetworkPrefix;
		
		exceptionNetworks = new ArrayList<String>();
		
		if (exceptionNetworkPrefix == null || exceptionNetworkPrefix.length() == 0) {
			return;
		} else {
			String[] split = exceptionNetworkPrefix.split("\\,");
			for (String s : split)
				exceptionNetworks.add(s);
		}
	}
	
	public String getExceptionNetworkGateway() {
		return exceptionNetworkGateway;
	}
	public void setExceptionNetworkGateway(String exceptionNetworkGateway) {
		this.exceptionNetworkGateway = exceptionNetworkGateway;
	}
	
	// IP 대역  2
	public String getExceptionNetworkPrefix2() {
		return exceptionNetworkPrefix2;
	}
	public void setExceptionNetworkPrefix2(String exceptionNetworkPrefix2) {
		this.exceptionNetworkPrefix2 = exceptionNetworkPrefix2;
		
		exceptionNetworks2 = new ArrayList<String>();
		
		if (exceptionNetworkPrefix2 == null || exceptionNetworkPrefix2.length() == 0) {
			return;
		} else {
			String[] split = exceptionNetworkPrefix2.split("\\,");
			for (String s : split)
				exceptionNetworks2.add(s);
		}
	}
	
	public String getExceptionNetworkGateway2() {
		return exceptionNetworkGateway2;
	}
	public void setExceptionNetworkGateway2(String exceptionNetworkGateway2) {
		this.exceptionNetworkGateway2 = exceptionNetworkGateway2;
	}
	
	@Override
	public void processRequest() {
		FieldBuffer inBuf = request.getFieldBuffer();
		FieldBuffer outBuf = response.getFieldBuffer();
		
		String originUrl = inBuf.getString("ORIGINURL");
		String clientIp = inBuf.getString("CLIENTIP");
		String serviceUrl = null;
		
		// CLIENT IP가 KT 장비 영역이면...
		if (clientIp == null) {
			serviceUrl = originUrl;
		} else {
			for (String s : exceptionNetworks) {
				if (clientIp.startsWith(s)) {
					serviceUrl = StringUtils.changeDomain(originUrl, exceptionNetworkGateway);
					outBuf.putString("SERVICEURL", serviceUrl);
					return;
				}
			}
			
			for (String s : exceptionNetworks2) {
				if (clientIp.startsWith(s)) {
					serviceUrl = StringUtils.changeDomain(originUrl, exceptionNetworkGateway2);
					outBuf.putString("SERVICEURL", serviceUrl);
					return;
				}
			}
			
			try {
				serviceUrl = SRUtils.routeURL(serviceRouterIp, originUrl, clientIp);
				if (serviceUrl == null) serviceUrl = originUrl;
			} catch (Exception e) {
				serviceUrl = originUrl;
			}
		}
		outBuf.putString("SERVICEURL", serviceUrl);
		
	}
	
	
	
}
