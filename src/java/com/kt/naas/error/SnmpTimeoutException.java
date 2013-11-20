package com.kt.naas.error;

public class SnmpTimeoutException extends RuntimeException {
	private static final long serialVersionUID = -5448662314361669246L;

	public SnmpTimeoutException()
	{
	}
	
	public SnmpTimeoutException(String errorMessage)
	{
		super(errorMessage);
	}
	
	public String toString()
	{
		return "SnmpTimeoutException(" + this.getMessage() + ")";
	}
}
