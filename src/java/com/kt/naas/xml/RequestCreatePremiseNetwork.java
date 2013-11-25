package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestCreatePremiseNetwork {
	@XmlElement( name = "TenantName")
	private String tenantName;
	
	@XmlElement( name = "NetworkName")
	private String networkname;
	
	@XmlElement( name = "Bandwidth")
	private int bandwidth;
	
	@XmlElement( name = "CpSvcId")
	private String cpSvcId;
	
	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getNetworkname() {
		return networkname;
	}

	public void setNetworkname(String networkname) {
		this.networkname = networkname;
	}

	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
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

	public String getCpSvcId() {
		return cpSvcId;
	}

	public void setCpSvcId(String cpSvcId) {
		this.cpSvcId = cpSvcId;
	}	
}
