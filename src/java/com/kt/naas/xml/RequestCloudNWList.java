package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*	"<?xml version=“1.0” encoding=“utf-8” ?>
<RequestToC name=“myRequest” description=""RequestToCloud"">
    <rid>DC-ID</rid>
    <cid>Tenant-ID</cid>
</RequestToC>"
*/

@XmlRootElement
public class RequestCloudNWList {

	String tenantName;
	String dcId;
	
	public String getTenantName() {
		return tenantName;
	}

	@XmlElement
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getDcid() {
		return dcId;
	}

	@XmlElement
	public void setDcid(String dcId) {
		this.dcId = dcId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestCloudNWList [rid=");
		builder.append(tenantName);
		builder.append(", cid=");
		builder.append(dcId);
		builder.append("]");
		return builder.toString();
	}
	
}
