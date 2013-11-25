package com.kt.naas.domain;

import java.util.ArrayList;

public class NetworkServiceRequest {
	String custId;
	String serviceName;
	String serviceType;
	String topologyType;
	String connType;
	String fromTime;
	String toTime;
	int bandwidth;

	private ArrayList<TenantNetworkInfo> networklist;

	public ArrayList<TenantNetworkInfo> getNetworklist() {
		return networklist;
	}

	public void setNetworklist(ArrayList<TenantNetworkInfo> networklist) {
		this.networklist = networklist;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getTopologyType() {
		return topologyType;
	}

	public void setTopologyType(String topologyType) {
		this.topologyType = topologyType;
	}

	public String getConnType() {
		return connType;
	}

	public void setConnType(String connType) {
		this.connType = connType;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}