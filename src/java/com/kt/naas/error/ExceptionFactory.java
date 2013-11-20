package com.kt.naas.error;

import com.kt.naas.util.MessageUtils;

public class ExceptionFactory {
	
	public static SNPException getSNPException(int errorCode)
	{
		String message = MessageUtils.getErrorMessage(errorCode, new String[0]);
		return new SNPException(errorCode, message);
	}

	public static SNPException getSNPException(int errorCode, String ...params)
	{
		String message = MessageUtils.getErrorMessage(errorCode, params);
		return new SNPException(errorCode, message);
	}
}
