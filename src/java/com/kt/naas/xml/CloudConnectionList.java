package com.kt.naas.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CloudConnectionList {
	@XmlElement(name = "Switch", type = CloudSwitch.class)
	ArrayList<CloudSwitch> connectionList;
	
	/* use in csdnCreateNWConnection.java */
	public ArrayList<CloudSwitch> getConnectionInfo() {
		return connectionList;
	}

	public void setConnectionInfo(ArrayList<CloudSwitch> connectionlist) {
		this.connectionList = connectionlist;
	}

}
