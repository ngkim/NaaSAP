package com.kt.naas.error;

public class SNPException extends RuntimeException {
	private static final long serialVersionUID = -8927997319069652790L;
	
	private final int errorCode;
	private final String errorMsg;
	
	public SNPException(int errorCode, String errorMsg)
	{
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
