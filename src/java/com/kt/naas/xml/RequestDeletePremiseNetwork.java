package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestDeletePremiseNetwork {
	
	@XmlElement(name = "TenantName")
	private String tenantname;
	
	@XmlElement(name = "CpSvcId")
	private String cpSvcId;
	
	public String getTenantname() {
		return tenantname;
	}

	public void setTenantname(String tenantname) {
		this.tenantname = tenantname;
	}

	public String getCpSvcId() {
		return cpSvcId;
	}

	public void setCpSvcId(String cpSvcId) {
		this.cpSvcId = cpSvcId;
	}	
}
