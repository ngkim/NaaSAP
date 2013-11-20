package com.kt.naas.domain;

public class TransportNetworkService {
	// CONSTRAINT t_nw_service_transport_pk PRIMARY KEY (transvcid, equipid,
	// associatedswid, svcid)

	// transvcid character varying(11) NOT NULL, -- Transport NW connection에 대한
	// 식별자(Transport SDN에서 생성)
	// nwname character varying(64), -- DC Network 명
	// svcid character varying(64) NOT NULL, -- 서비스에 대한 총괄 식별자
	private String transvcid;
	private String svcid;
	private String nwname;

	// pwlabelid character varying(64), -- Pseudo-wire Label ID
	private String pwlabelid;

	// equipid character varying(64) NOT NULL, -- Transport Equip ID
	// equipname character varying(64), -- Transport Equip 명
	// equipinboundportid character varying(64), -- Transport Equip up port ID
	// equipoutboundportid character varying(64), -- Transport Equip down port
	// ID
	// equipbw numeric(10,0), -- Transport Equip 대역폭(bps)
	// equipvlanid character varying(64), -- Transport Equip Vlan ID
	private String equipid;
	private String equipname;
	private String equipinboundportid;
	private String equipoutboundportid;
	private int equipbw;
	private String equipvlanid;

	// ethtype character varying(2), -- DC-DC, DC-Ent, Ent-Ent 카테고리별 코드
	// 정의(DD:DC-DC, DE:DC-Ent, EE:Ent-Ent, ...)
	// conntype character varying(3), -- Connection 유형(ETH:Ethernet(VLAN),
	// LSP:LSP(Tunnel), OTN:OTN, LAM:Lambda)
	// associatedswid character varying(64) NOT NULL, -- 해당 POTN이 연결된 장비SW ID
	// associatedswtype character varying(64), -- 해당 POTN이 연결된 SW가 DC인지 인빌딩인지
	// 구분자
	// qosofeir character varying(64), -- QoS Parameter : EIR(exceed infromation
	// rate) in POTN
	// qosofcir character varying(64), -- QoS Parameter : CIR(Committed
	// Information Rate) in POTN
	private String ethtype;
	private String conntype;
	private String associatedswid;
	private String associatedswtype;
	private String qosofeir;
	private String qosofcir;

	// status numeric(1,0) DEFAULT 0, -- 처리 결과 코드(-1:fail, 0:성공, 1:진행중)
	// resultmsg character varying(128), -- 처리 결과 메시지
	private int status;
	private String resultmsg;

	public String getTransvcid() {
		return transvcid;
	}

	public void setTransvcid(String transvcid) {
		this.transvcid = transvcid;
	}

	public String getSvcid() {
		return svcid;
	}

	public void setSvcid(String svcid) {
		this.svcid = svcid;
	}

	public String getNwname() {
		return nwname;
	}

	public void setNwname(String nwname) {
		this.nwname = nwname;
	}

	public String getPwlabelid() {
		return pwlabelid;
	}

	public void setPwlabelid(String pwlabelid) {
		this.pwlabelid = pwlabelid;
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

	public int getEquipbw() {
		return equipbw;
	}

	public void setEquipbw(int equipbw) {
		this.equipbw = equipbw;
	}

	public String getEquipvlanid() {
		return equipvlanid;
	}

	public void setEquipvlanid(String equipvlanid) {
		this.equipvlanid = equipvlanid;
	}

	public String getEthtype() {
		return ethtype;
	}

	public void setEthtype(String ethtype) {
		this.ethtype = ethtype;
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

}
