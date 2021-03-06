package com.kt.naas.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/*<?xml version="1.0" encoding="utf-8 ?>

 <VirtualNetworkList>	
 <VirtualNetwork>	
 <VirtualNetworkName>net_sdn</VirtualNetworkName>		
 <VirtualNetworkID>d2f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		
 <Subnet>10.1.1.0/24</Subnet>	
 </VirtualNetwork>   
 </VirtualNetworkList>
 */

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder={"name","subnet","VNID","bandwidth","ConnectionList"})
public class CloudVNID {
	@XmlElement(name = "NetworkName")
	String name;
	
	@XmlElement(name = "Subnet")
	String subnet;
	
	@XmlElement(name = "VLANID")
	String vnid; // virtual Network ID
	
	@XmlElement(name = "Bandwidth")
	String bandwidth;
	
	@XmlElement(name = "ConnectionList")
	CloudConnectionList connectionList; 
	
	public String getVNID() {
		return vnid;
	}

	// @XmlAttribute
	
	public void setVNID(String vnid) {
		this.vnid = vnid;
	}

	public String getName() {
		return name;
	}

	// @XmlAttribute
	
	public void setName(String name) {
		this.name = name;
	}

	public String getSubnet() {
		return subnet;
	}

	// @XmlAttribute
	
	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}

	public String getBandwidth() {
		return bandwidth;
	}

	
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

	public CloudConnectionList getConnectionList() {
		return connectionList;
	}

	public void setConnectionList(CloudConnectionList connectionList) {
		this.connectionList = connectionList;
	}
}