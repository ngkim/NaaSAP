package com.kt.naas.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Ethernet")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseCreateTransportNetwork {
	@XmlElement
	String status;
	
	@XmlAttribute
	String id;
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
