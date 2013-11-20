package com.kt.naas.xml;

import javax.xml.bind.annotation.XmlRootElement;

//<?xml version=??.0??encoding=?utf-8???>
//<CloudSDN>
//  <tid>ae1d027d63494cfc8c68fd11741deca3</tid>
//  <vnid>c59c6de5-9a6e-4f30-bc58-3e66d2f95649</vnid>
//	  <bw>100M</bw>
//</CloudSDN>

@XmlRootElement (name="CloudSDN")
public class RequestInfoCloudSDNConnnection {
	String tid;
	String vnid;
	String bw;
	
	public String getTid() {
		return tid;
	}
	
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public String getVnid() {
		return vnid;
	}
	
	public void setVnid(String vnid) {
		this.vnid = vnid;
	}
	
	public String getBw() {
		return bw;
	}
	
	public void setBw(String bw) {
		this.bw = bw;
	}
}
