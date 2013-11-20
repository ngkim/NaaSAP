package com.kt.naas.domain;

import java.sql.Timestamp;

public class NetworkService {
	private String svcid; // svcid character varying(64) NOT NULL, -- 서비스에 대한 총괄
							// 식별자(NaaS에서 생성)
	private String svctype; // svccategory character varying(2), -- DC-DC,
							// DC-Ent, Ent-Ent 카테고리별 코드 정의(DD, DE, EE, ...)
	private String svcname;
	private String topologytype; // topologytype character varying(6), -- 토폴로지
									// 유형(E-LINE, E-LAN, E-TREE)
	private String conntype; // conntype character varying(3), -- Connection
								// 유형(ETH:Ethernet(VLAN), LSP:LSP(Tunnel),
								// OTN:OTN, LAM:Lambda)
	private int bandwidth; // bandwidth numeric(10,0), -- 대역폭(bps)
	private String custid; // custid character varying(11), -- 고객 ID
	private String custname; // custname character varying(16), -- 고객 명
	private String contactpoint; // contactpoint character varying(12), -- 연락처
	private String address; // address character varying(256), -- 고객 주소
	private Timestamp begintime; // begintime timestamp without time zone, --
									// 서비스 시작일시
	private Timestamp endtime; // endtime timestamp without time zone, -- 서비스
								// 종료일시
	private String state;

	public String getSvcId() {
		return svcid;
	}

	public void setSvcId(String svcId) {
		this.svcid = svcId;
	}

	public String getTopologyType() {
		return topologytype;
	}

	public void setTopologyType(String topologyType) {
		this.topologytype = topologyType;
	}

	public String getConnType() {
		return conntype;
	}

	public void setConnType(String connType) {
		this.conntype = connType;
	}

	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getCustId() {
		return custid;
	}

	public void setCustId(String custId) {
		this.custid = custId;
	}

	public String getCustName() {
		return custname;
	}

	public void setCustName(String custName) {
		this.custname = custName;
	}

	public String getContactPoint() {
		return contactpoint;
	}

	public void setContactPoint(String contactPoint) {
		this.contactpoint = contactPoint;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getBeginTime() {
		return begintime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.begintime = beginTime;
	}

	public Timestamp getEndTime() {
		return endtime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endtime = endTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NetworkService [svcId=");
		builder.append(svcid);
		builder.append(", svcType=");
		builder.append(svctype);
		builder.append(", topologyType=");
		builder.append(topologytype);
		builder.append(", connType=");
		builder.append(conntype);
		builder.append(", bandwidth=");
		builder.append(bandwidth);
		builder.append(", custId=");
		builder.append(custid);
		builder.append(", custName=");
		builder.append(custname);
		builder.append(", contactPoint=");
		builder.append(contactpoint);
		builder.append(", address=");
		builder.append(address);
		builder.append(", beginTime=");
		builder.append(begintime);
		builder.append(", endTime=");
		builder.append(endtime);
		builder.append("]");
		return builder.toString();
	}

	public String getSvctype() {
		return svctype;
	}

	public void setSvctype(String svctype) {
		this.svctype = svctype;
	}

	public String getSvcName() {
		return svcname;
	}

	public void setSvcName(String svcname) {
		this.svcname = svcname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
