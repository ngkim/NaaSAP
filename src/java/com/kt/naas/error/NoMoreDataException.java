package com.kt.naas.error;

public class NoMoreDataException extends RuntimeException {
	private static final long serialVersionUID = -7356457050473953615L;
	
	public NoMoreDataException()
	{
		super("no more data");
	}
	
	public NoMoreDataException(String fieldName)
	{
		super("no more data in " + fieldName);
	}
}
