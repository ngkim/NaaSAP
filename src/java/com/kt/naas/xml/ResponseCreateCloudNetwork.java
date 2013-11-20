package com.kt.naas.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ResponseInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "returnCode", "message", "dcsvcid", "tenantid",
		"tenantname", "vnidlist" })
public class ResponseCreateCloudNetwork {

	@XmlElement(name = "NetworkList")
	private ArrayList<CloudVNID> vnidlist;

	@XmlElement(name = "DcSvcId")
	private String dcsvcid;

	@XmlElement(name = "TenantName")
	private String tenantname;

	@XmlElement(name = "TenantId")
	private String tenantid;

	@XmlElement(name = "ReturnCode")
	private String returnCode;

	@XmlElement(name = "ReturnCodeDescription")
	private String message;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returncode) {
		this.returnCode = returncode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDcsvcid() {
		return dcsvcid;
	}

	public void setDcsvcid(String dcsvcid) {
		this.dcsvcid = dcsvcid;
	}

	public String getTenantname() {
		return tenantname;
	}

	public void setTenantname(String tenantname) {
		this.tenantname = tenantname;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	/* use in csdnRetrieveNWList.java */
	public ArrayList<CloudVNID> getVNIDInfo() {
		return vnidlist;
	}

	public void setVNIDInfo(ArrayList<CloudVNID> vnidlist) {
		this.vnidlist = vnidlist;
	}

}