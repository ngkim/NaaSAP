package com.kt.naas.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = { "name", "ip", "swid", "upport", "downport", "vlanid",
//		"bandwidth" })
public class PremiseSwitchList {
	@XmlElement(name = "Switch")
	ArrayList<PremiseSwitch> pswList;

	public ArrayList<PremiseSwitch> getSw() {
		return pswList;
	}

	public void setSw(ArrayList<PremiseSwitch> pswList) {
		this.pswList = pswList;
	}
}