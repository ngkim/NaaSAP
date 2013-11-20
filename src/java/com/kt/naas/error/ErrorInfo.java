package com.kt.naas.error;

public class ErrorInfo {
	private final int errorCode;
	private final String errorMsg;
	
	public ErrorInfo(int errorCode, String errorMsg)
	{
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public String toString()
	{
		return "Error(" + errorCode + ":" + errorMsg + ")";
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
}
