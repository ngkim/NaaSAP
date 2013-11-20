package com.kt.naas.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement (name="ResponseInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseCreatePremiseNetwork {
	
	@XmlElement(name = "ReturnCode")
	private String returnCode;

	@XmlElement(name = "ReturnCodeDescription")
	private String message;
	
	@XmlElement(name="CpSvcId")
	  private String cpsvcid;
	
	@XmlElement(name="AttributeList")
	ArrayList<QoSAttributeList> attrList;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCpsvcid() {
		return cpsvcid;
	}

	public void setCpsvcid(String cpsvcid) {
		this.cpsvcid = cpsvcid;
	}

	public ArrayList<QoSAttributeList> getAttrList() {
		return attrList;
	}

	public void setAttrList(ArrayList<QoSAttributeList> attrList) {
		this.attrList = attrList;
	}
	
	
}
