package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="ResponseInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseDeletePremiseNetwork {
	@XmlElement(name = "ReturnCode")
	private String returnCode;

	@XmlElement(name = "ReturnCodeDescription")
	private String message;

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
	
	
}
