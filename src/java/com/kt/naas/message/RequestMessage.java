package com.kt.naas.message;

import java.io.Serializable;

import com.kt.naas.GlobalConstants;
import com.kt.naas.domain.FieldBuffer;

public class RequestMessage extends Message{
	private static final long serialVersionUID = -6377972112031430019L;

	protected String 	serviceName 	= null;
	protected String	userId			= null;
	protected String	userIp			= null;

	protected FieldBuffer	fieldBuffer	= new FieldBuffer();
	protected Serializable	userData	= null;

	public RequestMessage()
	{
		super();
		this.msgType = GlobalConstants.MSGTYPE_REQUEST;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
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

	@Override
	public String toString() {
		return String
				.format("RequestMessage [serviceName=%s, userId=%s, userIp=%s, userData=%s, senderType=%s, senderUID=%s, msgType=%s, msgSeqNo=%s, fieldBuffer\n%s]",
						serviceName, userId, userIp, userData,
						senderType, senderUID, msgType, msgSeqNo, fieldBuffer);
	}

	
	
	
}
