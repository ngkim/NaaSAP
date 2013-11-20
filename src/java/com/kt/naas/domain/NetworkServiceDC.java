package com.kt.naas.domain;

public class NetworkServiceDC {
	// CONSTRAINT t_nw_service_dc_pk PRIMARY KEY (dcsvcid, connid, svcid)
	private String dcsvcid; // dcsvcid character varying(11) NOT NULL, -- DC NW
							// connection에 대한 식별자(DC SDN에서 생성)
	private String nwname; // nwname character varying(64), -- DC Network 명
	private String connid; // connid character varying(64) NOT NULL, -- Network
							// 내 연결 ID
	private String svcid; // svcid character varying(64) NOT NULL, -- 소속 서비스
							// ID(t_nw_service 테이블 svcid)
	private String tenantid; // tenantid character varying(64), -- Tenant ID
	private String tenantname; // tenantname character varying(64), -- Tenant 명
	private String subnet; // subnet character varying(18), -- Subnet
	private String transequipid; // transequipid character varying(64), -- 연결된
									// Transport 장비 ID
	private String transequipportid; // transequipportid character varying(64),
										// -- 연결된 Transport 장비 Port ID
	private String l2id; // l2id character varying(64), -- L2 SW ID,
							// OF(OpenFlow) Aggregation Switch
	private String l2name; // l2name character varying(64), -- L2 SW 명,
							// OF(OpenFlow) Aggregation Switch
	private String l2ip; // l2ip character varying(15), -- L2 SW IP Address
	private String l2upportid; // l2upportid character varying(64), -- L2 SW up
								// port ID
	private String l2downportid; // l2downportid character varying(64), -- L2 SW
									// down port ID
	private int l2bw; // l2bw numeric(10,0), -- L2 SW 대역폭(bps)
	private String l2vlanid; // l2vlanid character varying(64), -- L2 SW Vlan ID
	private String torid; // torid character varying(64), -- ToR SW ID
	private String torname; // torname character varying(64), -- ToR SW 명
	private String torip; // torip character varying(15), -- ToR SW IP Address
	private String torupportid; // torupportid character varying(64), -- ToR SW
								// up port ID
	private int torbw; // torbw numeric(10,0), -- ToR SW 대역폭(bps)\
	private String torvlanid; // torvlanid character varying(64), -- ToR SW Vlan
								// ID
	private int status; // status numeric(1,0) DEFAULT 0, -- 처리 결과 코드(-1:fail,
						// 0:성공, 1:진행중)
	private String resultmsg; // resultmsg character varying(128), -- 처리 결과 메시지
	private String tordownportid; // tordownportid character varying(64), -- TOR
									// SW down Port, Client로 나가는 방향의 Port

	public String getDcsvcid() {
		return dcsvcid;
	}

	public void setDcsvcid(String dcsvcid) {
		this.dcsvcid = dcsvcid;
	}

	public String getNwname() {
		return nwname;
	}

	public void setNwname(String nwname) {
		this.nwname = nwname;
	}

	public String getConnid() {
		return connid;
	}

	public void setConnid(String connid) {
		this.connid = connid;
	}

	public String getSvcid() {
		return svcid;
	}

	public void setSvcid(String svcid) {
		this.svcid = svcid;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public String getTenantname() {
		return tenantname;
	}

	public void setTenantname(String tenantname) {
		this.tenantname = tenantname;
	}

	public String getSubnet() {
		return subnet;
	}

	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}

	public String getTransequipid() {
		return transequipid;
	}

	public void setTransequipid(String transequipid) {
		this.transequipid = transequipid;
	}

	public String getTransequipportid() {
		return transequipportid;
	}

	public void setTransequipportid(String transequipportid) {
		this.transequipportid = transequipportid;
	}

	public String getL2id() {
		return l2id;
	}

	public void setL2id(String l2id) {
		this.l2id = l2id;
	}

	public String getL2name() {
		return l2name;
	}

	public void setL2name(String l2name) {
		this.l2name = l2name;
	}

	public String getL2ip() {
		return l2ip;
	}

	public void setL2ip(String l2ip) {
		this.l2ip = l2ip;
	}

	public String getL2upportid() {
		return l2upportid;
	}

	public void setL2upportid(String l2upportid) {
		this.l2upportid = l2upportid;
	}

	public String getL2downportid() {
		return l2downportid;
	}

	public void setL2downportid(String l2downportid) {
		this.l2downportid = l2downportid;
	}

	public int getL2bw() {
		return l2bw;
	}

	public void setL2bw(int l2bw) {
		this.l2bw = l2bw;
	}

	public String getL2vlanid() {
		return l2vlanid;
	}

	public void setL2vlanid(String l2vlanid) {
		this.l2vlanid = l2vlanid;
	}

	public String getTorid() {
		return torid;
	}

	public void setTorid(String torid) {
		this.torid = torid;
	}

	public String getTorname() {
		return torname;
	}

	public void setTorname(String torname) {
		this.torname = torname;
	}

	public String getTorip() {
		return torip;
	}

	public void setTorip(String torip) {
		this.torip = torip;
	}

	public String getTorupportid() {
		return torupportid;
	}

	public void setTorupportid(String torupportid) {
		this.torupportid = torupportid;
	}

	public int getTorbw() {
		return torbw;
	}

	public void setTorbw(int torbw) {
		this.torbw = torbw;
	}

	public String getTorvlanid() {
		return torvlanid;
	}

	public void setTorvlanid(String torvlanid) {
		this.torvlanid = torvlanid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getResultmsg() {
		return resultmsg;
	}

	public void setResultmsg(String resultmsg) {
		this.resultmsg = resultmsg;
	}

	public String getTordownportid() {
		return tordownportid;
	}

	public void setTordownportid(String tordownportid) {
		this.tordownportid = tordownportid;
	}

}
