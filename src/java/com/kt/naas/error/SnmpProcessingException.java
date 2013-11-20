package com.kt.naas.error;

public class SnmpProcessingException extends RuntimeException {
	private static final long serialVersionUID = -8952605704705938384L;
	public static final SnmpProcessingException VBSIZE_MISMATCH 
			= new SnmpProcessingException("variable binding size mismatch.");
	
	public SnmpProcessingException()
	{
	}
	
	public SnmpProcessingException(String errorMessage)
	{
		super(errorMessage);
	}
	
	public String toString()
	{
		return "SnmpProcessingException(" + this.getMessage() + ")";
	}
}
