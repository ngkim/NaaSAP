package com.kt.naas.domain;

public class TenantNetworkInfo {
	String tenantName;
	String nwName;
	String dcId;
	String dcName;

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getNwName() {
		return nwName;
	}

	public void setNwName(String nwName) {
		this.nwName = nwName;
	}

	public String getDcId() {
		return dcId;
	}

	public void setDcId(String dcId) {
		this.dcId = dcId;
	}

	public String getDcName() {
		return dcName;
	}

	public void setDcName(String dcName) {
		this.dcName = dcName;
	}
}
