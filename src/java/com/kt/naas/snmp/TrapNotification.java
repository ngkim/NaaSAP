package com.kt.naas.snmp;

import java.io.Serializable;

import org.snmp4j.PDU;

public class TrapNotification implements Serializable{
	private static final long serialVersionUID = 4063821341412893217L;

	private String	trapOid = "";
	private String	sourceIp = "";
	private int		sourcePort = 0;
	private String	recvTime = "";
	private long	recvTimeMills = 0L;
	private PDU		pdu = null;
	
	public String getTrapOid() {
		return trapOid;
	}
	public void setTrapOid(String trapOid) {
		this.trapOid = trapOid;
	}
	public String getSourceIp() {
		return sourceIp;
	}
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}
	public int getSourcePort() {
		return sourcePort;
	}
	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}
	public String getRecvTime() {
		return recvTime;
	}
	public void setRecvTime(String recvTime) {
		this.recvTime = recvTime;
	}
	public long getRecvTimeMills() {
		return recvTimeMills;
	}
	public void setRecvTimeMills(long recvTimeMills) {
		this.recvTimeMills = recvTimeMills;
	}
	public PDU getPdu() {
		return pdu;
	}
	public void setPdu(PDU pdu) {
		this.pdu = pdu;
	}
	@Override
	public String toString() {
		
		return String.format("TrapNotification [recvTime=%s, source=%s/%d, trapOid=%s]"
				, recvTime, sourceIp, sourcePort, trapOid);
	}
}
