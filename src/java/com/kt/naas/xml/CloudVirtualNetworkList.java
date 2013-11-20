package com.kt.naas.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CloudVirtualNetworkList {
	@XmlElement(name = "VirtualNetwork")
	private ArrayList<CloudVirtualNetwork> vnlist;

	public ArrayList<CloudVirtualNetwork> getVnlist() {
		return vnlist;
	}

	public void setVnlist(ArrayList<CloudVirtualNetwork> vnlist) {
		this.vnlist = vnlist;
	}

}
