package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * <?xml version=“1.0” encoding=“utf-8” ?>
 * <Ethernet name=“myEth” description="DC to DC tunnel">
 * <rid>requester-ID</rid> 
 * <cid>customer-ID</cid>
 * <eType>E-LAN</eType>
 * 		<UNIPeer id="switch1-ID" port="10" vlan="1001"/>
 * 		<UNIPeer id="switch2-ID" port="20" vlan="1002"/>
 * 		<UNIPeer id="switch3-ID" port="30" vlan="1003"/>
 * 		<QoS bandwidth=“1G” exceed=“100M”/>
 * </Ethernet>
 */
@XmlRootElement(name = "Ethernet")
public class RequestCreateTransportNetwork {
	String rid;
	String cid;
	String eid;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

}