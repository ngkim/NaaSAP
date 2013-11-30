package com.kt.naas.xml;

import java.util.ArrayList;

/*<?xml version="1.0" encoding="utf-8 ?>
 <ResponseInfo>   
 <ReturnCode>200</ReturnCode>   
 <ReturnCodeDescription>Success</ReturnCodeDescription>   
 <TenantId>0be2a9fc8a7040d8963c9f67f521bdab</TenantId>   
 <TenantName>cloudsdn</TenantName>   
 <VirtualNetworkList>	
 <VirtualNetwork>	
 <VirtualNetworkName>net_sdn</VirtualNetworkName>		
 <VirtualNetworkID>d2f1b6e4-8721-4b66-a64a-6728d39862c2</VirtualNetworkID>		
 <Subnet>10.1.1.0/24</Subnet>	
 </VirtualNetwork>   
 </VirtualNetworkList>
 </ResponseInfo>*/


import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ResponseInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "returnCode", "message", "tenantid", "tenantname", "vnidlist" })
public class ResponseCloudNWList {

	@XmlElement(name = "VirtualNetworkList")
	private ArrayList<CloudVirtualNetwork> vnidlist;

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
	
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	/*public String getDcsvcid() {
		return dcsvcid;
	}*/

	public void setTenantname(String tenantname) {
		this.tenantname = tenantname;
	}
	
	public String getTenantname() {
		return tenantname;
	}

	public String getTenantid() {
		return tenantid;
	}
	
	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

 	/* use in csdnRetrieveNWList.java */
	public ArrayList<CloudVirtualNetwork> getVNIDInfo() {
		return vnidlist;
	}
	
	public void setVNIDInfo(ArrayList<CloudVirtualNetwork> vnidlist) {
		this.vnidlist = vnidlist;
	}

	/* use in csdnCreateNWConnection.java */
	/*
	 * public ArrayList<CloudSwitch> getConnectionInfo() { return
	 * connectionlist; }
	 * 
	 * @XmlElement(name = "ConnectionSwitchList") public void
	 * setConnectionInfo(ArrayList<CloudSwitch> connectionlist) {
	 * this.connectionlist = connectionlist; }
	 * 
	 * public String getSubnet() { return subnet; }
	 * 
	 * @XmlElement(name = "Subnet") public void setSubnet(String subnet) {
	 * this.subnet = subnet; }
	 */

	/*
	 * @XmlElement(name="DcSvcId") public void setDcsvcid(String dcsvcid) {
	 * this.dcsvcid = dcsvcid; }
	 * 
	 * public String getTenantname() { return tenantname; }
	 */

}
