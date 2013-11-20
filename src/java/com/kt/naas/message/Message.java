package com.kt.naas.message;

import java.io.Serializable;

import com.kt.naas.GlobalConstants;

public class Message implements GlobalConstants, Serializable {
	private static final long serialVersionUID = 1625039130331761965L;

	protected String	senderType 	= ACTOR_WEBUI;
	protected String	senderUID 	= null;
	protected String	targetType 	= ACTOR_AP;
	protected String	targetUID 	= null;
	
	protected String	msgType 	= MSGTYPE_RESPONSE;
	protected long		msgSeqNo 	= 0L;
	
	public String getSenderType() {
		return senderType;
	}
	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}
	public String getSenderUID() {
		return senderUID;
	}
	public void setSenderUID(String senderUID) {
		this.senderUID = senderUID;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getTargetUID() {
		return targetUID;
	}
	public void setTargetUID(String targetUID) {
		this.targetUID = targetUID;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public long getMsgSeqNo() {
		return msgSeqNo;
	}
	public void setMsgSeqNo(long msgSeqNo) {
		this.msgSeqNo = msgSeqNo;
	}
	
}
