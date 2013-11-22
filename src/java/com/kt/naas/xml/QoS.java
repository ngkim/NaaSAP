package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class QoS {
	@XmlAttribute
	int bandwidth;

	@XmlAttribute
	int exceed;

	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

	public int getExceed() {
		return exceed;
	}

	public void setExceed(int exceed) {
		this.exceed = exceed;
	}
}
