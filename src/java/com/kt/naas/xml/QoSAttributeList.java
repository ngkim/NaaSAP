package com.kt.naas.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class QoSAttributeList {
	@XmlElement(name = "Attribute")
	private ArrayList<QoSAttribute> qosAttribute;

	public ArrayList<QoSAttribute> getQosAttribute() {
		return qosAttribute;
	}

	public void setQosAttribute(ArrayList<QoSAttribute> qosAttribute) {
		this.qosAttribute = qosAttribute;
	}

	
}
