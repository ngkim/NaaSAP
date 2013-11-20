package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/*
 * 				<SWName>4F_Partion</SWName>
				<SWType>End-Point_Switch</SWType>
				<SWID>cvbvxc34653</SWID>
				<Ip>30.30.30.30</Ip>
				<UpPort>1</UpPort>
				<DownPort>2</DownPort>
 */
@XmlRootElement(name = "Switch")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = { "name", "ip", "swid", "upport", "downport", "vlanid",
//		"bandwidth" })
public class PremiseSwitch {
	@XmlElement(name = "SWName")
	String name;
	
	@XmlElement(name = "SWType")
	String type;
	
	@XmlElement(name = "SWID")
	String swid;
	
	@XmlElement(name = "Ip")
	String ip;
	
	@XmlElement(name = "UpPort")
	String upport;
	
	@XmlElement(name = "DownPort")
	String downport;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSwid() {
		return swid;
	}

	public void setSwid(String swid) {
		this.swid = swid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUpport() {
		return upport;
	}

	public void setUpport(String upport) {
		this.upport = upport;
	}

	public String getDownport() {
		return downport;
	}

	public void setDownport(String downport) {
		this.downport = downport;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}