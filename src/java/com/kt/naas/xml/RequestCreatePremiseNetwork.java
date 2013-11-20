package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestCreatePremiseNetwork {
	private String tenantName;
	private String networkname;
	private String bandwidth;
	
	public String getTenantName() {
		return tenantName;
	}

	@XmlElement
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getNetworkname() {
		return networkname;
	}

	public void setNetworkname(String networkname) {
		this.networkname = networkname;
	}

	public String getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestCreatePremiseNetwork [tenantName=");
		builder.append(tenantName);
		builder.append(", networkname=");
		builder.append(networkname);
		builder.append(", bandwidth=");
		builder.append(bandwidth);
		builder.append("]");
		return builder.toString();
	}	
}
