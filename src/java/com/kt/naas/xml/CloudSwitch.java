package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"name","dpid","type","uplinkport","downlinkport"})
public class CloudSwitch {
	String name;
	String dpid; //datapath id
	String type;
//	String ip;
	String uplinkport;
	String downlinkport;

	public String getName() {
		return name;
	}

	//@XmlAttribute
	@XmlElement(name="SWName")
	public void setName(String name) {
		this.name = name;
	}

	public String getDpid() {
		return dpid;
	}

	//@XmlAttribute
	@XmlElement(name="SWID")
	public void setDpid(String dpid) {
		this.dpid = dpid;
	}
	
	/*public String getIp() {
		return ip;
	}

	//@XmlAttribute
	@XmlElement(name="Ip")
	public void setIp(String ip) {
		this.ip = ip;
	}*/

	public String getUplinkport() {
		return uplinkport;
	}

	//@XmlAttribute
	@XmlElement(name="UpPort")
	public void setUplinkport(String uplinkport) {
		this.uplinkport = uplinkport;
	}
	
	public String getDownlinkport() {
		return downlinkport;
	}

	//@XmlAttribute
	@XmlElement(name="DownPort")
	public void setDownlinkport(String downlinkport) {
		this.downlinkport = downlinkport;
	}

	public String getType() {
		return type;
	}

	@XmlElement(name="SWType")
	public void setType(String type) {
		this.type = type;
	}
}