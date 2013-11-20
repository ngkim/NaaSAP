package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*"<?xml version=“1.0” encoding=“utf-8” ?>
<RequestToC name=“myRequest” description=""RequestToCloud"">
    <cid>Tenant-ID</cid>
    <vnid>VNID</vnid>
    <QoS bandwidth=“500M” exceed=“100M”/>
</RequestToC>"
*/

@XmlRootElement
public class RequestCreateCloudNetwork {
	String tenantName;
	String vnid;
	String qos;
	
	public String getTenantName() {
		return tenantName;
	}

	@XmlElement
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getVnid() {
		return vnid;
	}

	public void setVnid(String vnid) {
		this.vnid = vnid;
	}

	public String getQos() {
		return qos;
	}

	public void setQos(String qos) {
		this.qos = qos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestCreateCloudNetwork [tenantName=");
		builder.append(tenantName);
		builder.append(", vnid=");
		builder.append(vnid);
		builder.append(", qos=");
		builder.append(qos);
		builder.append("]");
		return builder.toString();
	}
}
