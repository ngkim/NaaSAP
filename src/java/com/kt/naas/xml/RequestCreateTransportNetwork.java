package com.kt.naas.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/*
 <?xml version=“1.0” encoding=“utf-8” ?>
 <Ethernet name=“myEth” description="DC to DC tunnel">
 <rid>requester-ID</rid>
 <cid>customer-ID</cid>
 <eType>E-LAN</eType>
 <UNIPeer id="switch1-ID" port="10" vlan="1001"/>
 <UNIPeer id="switch2-ID" port="20" vlan="1002"/>
 <UNIPeer id="switch3-ID" port="30" vlan="1003"/>
 <QoS bandwidth=“1G” exceed=“100M”/>
 </Ethernet>
 */
@XmlRootElement(name = "Ethernet")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestCreateTransportNetwork {
	@XmlAttribute
	String name;
	
	@XmlAttribute
	String description;

	@XmlElement
	String rid;

	@XmlElement
	String cid;

	@XmlElement
	String eType;

	@XmlElement(name = "UNIPeer")
	ArrayList<UNIPeer> peers;

	@XmlElement(name = "QoS")
	QoS qos;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public String geteType() {
		return eType;
	}

	public void seteType(String eType) {
		this.eType = eType;
	}

	public ArrayList<UNIPeer> getPeers() {
		return peers;
	}

	public void setPeers(ArrayList<UNIPeer> peers) {
		this.peers = peers;
	}

	public QoS getQos() {
		return qos;
	}

	public void setQos(QoS qos) {
		this.qos = qos;
	}

}
