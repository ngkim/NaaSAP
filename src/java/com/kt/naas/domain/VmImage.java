package com.kt.naas.domain;

public class VmImage {
	private String	serverid;
	private String	custid;
	private String	imageid;
	private String	imagename;
	private String	href;
	private String	issn;
	@Override
	public String toString() {
		return String
				.format("VmImage [serverid=%s, custid=%s, imageid=%s, imagename=%s, href=%s, issn=%s]",
						serverid, custid, imageid, imagename, href, issn);
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getImageid() {
		return imageid;
	}
	public void setImageid(String imageid) {
		this.imageid = imageid;
	}
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getIssn() {
		return issn;
	}
	public void setIssn(String issn) {
		this.issn = issn;
	}
	
}
