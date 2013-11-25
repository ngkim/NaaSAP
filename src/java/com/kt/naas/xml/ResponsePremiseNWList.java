package com.kt.naas.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// <?xml version="1.0" encoding="UTF-8"?><ResponseInfo><ReturnCode>200</ReturnCode><ReturnCodeDescription>Success</ReturnCodeDescription><CpSvcId>CSDN000001</CpSvcId><TenantId>A111222333</TenantId><TenantName>NH_ADMIN</TenantName><NetworkList><NetworkName>NH_PrivateNW1</NetworkName><Subnet>100.100.100.100/24</Subnet><VLANID>10</VLANID><Bandwidth>100M</Bandwidth><ConnectionList><Switch><SWName>4F_Partion</SWName><SWType>End-Point_Switch</SWType><SWID>cvbvxc34653</SWID><Ip>30.30.30.30</Ip><UpPort>1</UpPort><DownPort>2</DownPort></Switch><Switch><SWName>3F_L2_2211</SWName><SWType>L2_Switch</SWType><SWID>asfhkjas4234</SWID><Ip>20.20.20.20</Ip><UpPort>2</UpPort><DownPort>4</DownPort></Switch><Switch><SWName>3F_TransportSW</SWName><SWType>Aggregate_Switch</SWType><SWID>bbc11112222</SWID><Ip>10.10.10.10</Ip><UpPort>2</UpPort><DownPort>3</DownPort></Switch></ConnectionList></NetworkList></ResponseInfo>

@XmlRootElement(name = "ResponseInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "returnCode", "message", "cpsvcid", "tenantid",
		"tenantname", "neossId", "vnidlist" })
public class ResponsePremiseNWList {
	@XmlElement(name = "ReturnCode")
	private String returnCode;

	@XmlElement(name = "ReturnCodeDescription")
	private String message;

	@XmlElement(name = "CpSvcId")
	private String cpsvcid;

	@XmlElement(name = "TenantName")
	private String tenantname;

	@XmlElement(name = "TenantId")
	private String tenantid;

	@XmlElement(name = "NetworkList")
	private ArrayList<PremiseNetwork> vnidlist;

	@XmlElement(name = "NeossID")
	private String neossId;

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

	public ArrayList<PremiseNetwork> getVnidlist() {
		return vnidlist;
	}

	public void setVnidlist(ArrayList<PremiseNetwork> vnidlist) {
		this.vnidlist = vnidlist;
	}

	public String getNeossId() {
		return neossId;
	}

	public void setNeossId(String neossId) {
		this.neossId = neossId;
	}

}
