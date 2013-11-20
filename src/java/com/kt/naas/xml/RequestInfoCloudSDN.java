package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlRootElement;

//<?xml version=??.0??encoding=?utf-8???>
//<CloudSDN>
//<tid>ae1d027d63494cfc8c68fd11741import javax.xml.bind.annotation.XmlRootElement;

//<?xml version=??.0??encoding=?utf-8???>
//<CloudSDN>
//  <dcid>123456</dcid>
//  <tid>ae1d027d63494cfc8c68fd11741deca3</tid>
//</CloudSDN>

@XmlRootElement (name="CloudSDN")
public class RequestInfoCloudSDN {
	String dcid;
	String tid;
	
	public String getDcid() {
		return dcid;
	}
	
	public void setDcid(String dcid) {
		this.dcid = dcid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

}


