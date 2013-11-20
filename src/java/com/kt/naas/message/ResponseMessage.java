package com.kt.naas.message;

import java.io.Serializable;

import com.kt.naas.domain.FieldBuffer;

public class ResponseMessage extends Message{
	private static final long serialVersionUID = 8001901639857404275L;

	protected String 	serviceName 	= null;
	protected int		resultCode		= 0;
	protected String	resultMessage	= "success";

	protected FieldBuffer	fieldBuffer	= new FieldBuffer();
	protected Serializable	userData	= null;

	public ResponseMessage()
	{
	}
	
	public ResponseMessage(RequestMessage request)
	{
		senderType 	= ACTOR_AP;
		senderUID 	= null;
		targetType 	= request.getSenderType();
		targetUID 	= request.getSenderUID();
		
		msgType 	= MSGTYPE_RESPONSE;
		msgSeqNo 	= request.getMsgSeqNo();
		serviceName	= request.getServiceName();
	}
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public FieldBuffer getFieldBuffer() {
		return fieldBuffer;
	}
	public void setFieldBuffer(FieldBuffer fieldBuffer) {
		this.fieldBuffer = fieldBuffer;
	}
	public Serializable getUserData() {
		return userData;
	}
	public void setUserData(Serializable userData) {
		this.userData = userData;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	@Override
	public String toString() {
		return String
				.format("ResponseMessage [serviceName=%s, resultCode=%s, resultMessage=%s, userData=%s, targetType=%s, targetUID=%s, msgType=%s, msgSeqNo=%s, fieldBuffer\n%s]",
						serviceName, resultCode, resultMessage,
						userData, targetType, targetUID, msgType, msgSeqNo, fieldBuffer);
	}
	
}
