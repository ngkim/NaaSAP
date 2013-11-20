package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/*
 * <Attribute>
<AttributeName>Bandwidth</AttributeName>
<PreviousValue>100M</PreviousValue>
<UpdateValue>1G</UpdateValue>
</Attribute>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class QoSAttribute {
	@XmlElement(name = "AttributeName")
	private String attrName;
	
	@XmlElement(name = "PreviousValue")
	private String prevValue;
	
	@XmlElement(name = "UpdateValue")
	private String updateValue;

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getPrevValue() {
		return prevValue;
	}

	public void setPrevValue(String prevValue) {
		this.prevValue = prevValue;
	}

	public String getUpdateValue() {
		return updateValue;
	}

	public void setUpdateValue(String updateValue) {
		this.updateValue = updateValue;
	}
}
