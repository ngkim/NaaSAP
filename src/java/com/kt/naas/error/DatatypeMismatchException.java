package com.kt.naas.error;

public class DatatypeMismatchException extends RuntimeException {
	private static final long serialVersionUID = 7703099021210921731L;

	public DatatypeMismatchException()
	{
		super("datatype mismatch");
	}
	
	public DatatypeMismatchException(String fieldName)
	{
		super("datatype mismatch in " + fieldName);
	}
	
	public DatatypeMismatchException(String fieldName, Object o)
	{
		super("datatype mismatch in " + fieldName + "(" + o.getClass().getSimpleName() + ")");
	}
}
