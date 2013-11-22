package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class UNIPeer {
	@XmlAttribute
	String id;

	@XmlAttribute
	String port;

	@XmlAttribute
	String vlan;
	
	@XmlElement(name = "UNIME")
	UNIME u;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getVlan() {
		return vlan;
	}

	public void setVlan(String vlan) {
		this.vlan = vlan;
	}
	
	public UNIME getU() {
		return u;
	}

	public void setU(UNIME u) {
		this.u = u;
	}
}