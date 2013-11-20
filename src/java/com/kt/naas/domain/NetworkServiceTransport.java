package com.kt.naas.domain;

public class NetworkServiceTransport {
	// CONSTRAINT t_nw_service_transport_pk PRIMARY KEY (transvcid, equipid,
	// associatedswid, svcid)

	private String transvcid; // transvcid character varying(11) NOT NULL, --
								// Transport NW connection에 대한 식별자(Transport
								// SDN에서 생성)
	private String nwname; // nwname character varying(64), -- DC Network 명
	private String svcid; // svcid character varying(64) NOT NULL, -- 서비스에 대한 총괄
							// 식별자

	private String equipid; // equipid character varying(64) NOT NULL, --
							// Transport Equip ID
	private String equipname; // equipname character varying(64), -- Transport
								// Equip 명
	private String equipinboundportid; // equipinboundportid character
										// varying(64), -- Transport Equip up
										// port ID
	private String equipoutboundportid; // equipoutboundportid character
										// varying(64), -- Transport Equip down
										// port ID
	private String equipvlanid; // equipvlanid character varying(64), --
								// Transport Equip Vlan ID
	private int equipbw; // equipbw numeric(10,0), -- Transport Equip 대역폭(bps)

	private String ethtype; // ethtype character varying(2), -- DC-DC, DC-Ent,
							// Ent-Ent 카테고리별 코드 정의(DD:DC-DC, DE:DC-Ent,
							// EE:Ent-Ent, ...)
	private String pwlabelid; // pwlabelid character varying(64), -- Pseudo-wire
								// Label ID
	private String conntype; // conntype character varying(3), -- Connection
								// 유형(ETH:Ethernet(VLAN), LSP:LSP(Tunnel),
								// OTN:OTN, LAM:Lambda)
	private String associatedswid; // associatedswid character varying(64) NOT
									// NULL, -- 해당 POTN이 연결된 장비SW ID
	private String associatedswtype; // associatedswtype character varying(64),
										// -- 해당 POTN이 연결된 SW가 DC인지 인빌딩인지 구분자
	private String qosofeir; // qosofeir character varying(64), -- QoS Parameter
								// : EIR(exceed infromation rate) in POTN
	private String qosofcir; // qosofcir character varying(64), -- QoS Parameter
								// : CIR(Committed Information Rate) in POTN

	private int stauts; // status numeric(1,0) DEFAULT 0, -- 처리 결과 코드(-1:fail,
						// 0:성공, 1:진행중)
	private String resultmsg; // resultmsg character varying(128), -- 처리 결과 메시지

	public String getTransvcid() {
		return transvcid;
	}

	public void setTransvcid(String transvcid) {
		this.transvcid = transvcid;
	}

	public String getNwname() {
		return nwname;
	}

	public void setNwname(String nwname) {
		this.nwname = nwname;
	}

	public String getSvcid() {
		return svcid;
	}

	public void setSvcid(String svcid) {
		this.svcid = svcid;
	}

	public String getEquipid() {
		return equipid;
	}

	public void setEquipid(String equipid) {
		this.equipid = equipid;
	}

	public String getEquipname() {
		return equipname;
	}

	public void setEquipname(String equipname) {
		this.equipname = equipname;
	}

	public String getEquipinboundportid() {
		return equipinboundportid;
	}

	public void setEquipinboundportid(String equipinboundportid) {
		this.equipinboundportid = equipinboundportid;
	}

	public String getEquipoutboundportid() {
		return equipoutboundportid;
	}

	public void setEquipoutboundportid(String equipoutboundportid) {
		this.equipoutboundportid = equipoutboundportid;
	}

	public String getEquipvlanid() {
		return equipvlanid;
	}

	public void setEquipvlanid(String equipvlanid) {
		this.equipvlanid = equipvlanid;
	}

	public int getEquipbw() {
		return equipbw;
	}

	public void setEquipbw(int equipbw) {
		this.equipbw = equipbw;
	}

	public String getEthtype() {
		return ethtype;
	}

	public void setEthtype(String ethtype) {
		this.ethtype = ethtype;
	}

	public String getPwlabelid() {
		return pwlabelid;
	}

	public void setPwlabelid(String pwlabelid) {
		this.pwlabelid = pwlabelid;
	}

	public String getConntype() {
		return conntype;
	}

	public void setConntype(String conntype) {
		this.conntype = conntype;
	}

	public String getAssociatedswid() {
		return associatedswid;
	}

	public void setAssociatedswid(String associatedswid) {
		this.associatedswid = associatedswid;
	}

	public String getAssociatedswtype() {
		return associatedswtype;
	}

	public void setAssociatedswtype(String associatedswtype) {
		this.associatedswtype = associatedswtype;
	}

	public String getQosofeir() {
		return qosofeir;
	}

	public void setQosofeir(String qosofeir) {
		this.qosofeir = qosofeir;
	}

	public String getQosofcir() {
		return qosofcir;
	}

	public void setQosofcir(String qosofcir) {
		this.qosofcir = qosofcir;
	}

	public int getStauts() {
		return stauts;
	}

	public void setStauts(int stauts) {
		this.stauts = stauts;
	}

	public String getResultmsg() {
		return resultmsg;
	}

	public void setResultmsg(String resultmsg) {
		this.resultmsg = resultmsg;
	}

}
