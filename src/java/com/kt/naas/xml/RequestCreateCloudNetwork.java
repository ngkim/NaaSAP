package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*"<?xml version=“1.0” encoding=“utf-8” ?>
<RequestToC name=“myRequest” description=""RequestToCloud"">
    <cid>Tenant-ID</cid>
    <vnid>VNID</vnid>
    <QoS bandwidth=“500M” exceed=“100M”/>
</RequestToC>"
*/

@XmlRootElement(name = "CloudSDN")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestCreateCloudNetwork {
	String tid;
	String vnid;
	String bw;
	
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getVnid() {
		return vnid;
	}

	public void setVnid(String vnid) {
		this.vnid = vnid;
	}

	public String getBw() {
		return bw;
	}

	public void setBw(String bw) {
		this.bw = bw;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestCreateCloudNetwork [tenantName=");
		builder.append(tid);
		builder.append(", vnid=");
		builder.append(vnid);
		builder.append(", qos=");
		builder.append(bw);
		builder.append("]");
		return builder.toString();
	}
}
