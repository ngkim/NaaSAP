package com.kt.naas.domain;

public class ProgressStatus {
	private int totalcnt; 		// totalcnt numeric(2,0),
	private int currentcnt;		// currentcnt numeric(2,0),
	private String custid;		// custid character varying(64) NOT NULL,
	private String processmsg;	// processmsg character varying(2000)
	
	public int getTotalcnt() {
		return totalcnt;
	}
	
	public void setTotalcnt(int totalcnt) {
		this.totalcnt = totalcnt;
	}
	
	public int getCurrentcnt() {
		return currentcnt;
	}
	
	public void setCurrentcnt(int currentcnt) {
		this.currentcnt = currentcnt;
	}
	
	public String getCustid() {
		return custid;
	}
	
	public void setCustid(String custid) {
		this.custid = custid;
	}
	
	public String getProcessmsg() {
		return processmsg;
	}
	
	public void setProcessmsg(String processmsg) {
		this.processmsg = processmsg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProgressStatus [totalcnt=");
		builder.append(totalcnt);
		builder.append(", currentcnt=");
		builder.append(currentcnt);
		builder.append(", custid=");
		builder.append(custid);
		builder.append(", processmsg=");
		builder.append(processmsg);
		builder.append("]");
		return builder.toString();
	}
	
}
