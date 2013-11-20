package com.kt.naas.error;

public class SnmpException extends RuntimeException {
	private static final long serialVersionUID = 5334450291001712971L;
	private int errorIndex = 0;
	private int errorStatus = 0;
	
	public SnmpException(int errorIndex, int errorStatus, String errorMessage)
	{
		super(errorMessage);
		this.errorIndex = errorIndex;
		this.errorStatus = errorStatus;
	}
	
	public SnmpException(String errorMessage)
	{
		super(errorMessage);
	}
	
	
	public int getErrorIndex() {
		return errorIndex;
	}

	public void setErrorIndex(int errorIndex) {
		this.errorIndex = errorIndex;
	}

	public int getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(int errorStatus) {
		this.errorStatus = errorStatus;
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("SNMP_Exception[");
		if (errorIndex != 0 && errorStatus != 0)
		{
			buf.append("ErrIdx=").append(errorIndex);
			buf.append(",ErrStatus=").append(errorStatus).append(",");
		}
		buf.append("ErrMsg=").append(super.getMessage()).append("]");
		
		return buf.toString();
	}
}