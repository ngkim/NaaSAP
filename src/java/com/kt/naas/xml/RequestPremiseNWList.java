package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestPremiseNWList {

	String tenantName;
	
	public String getTenantName() {
		return tenantName;
	}

	@XmlElement
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestPremiseNWList [tenantName=");
		builder.append(tenantName);
		builder.append("]");
		return builder.toString();
	}
	
}
