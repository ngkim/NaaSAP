package com.kt.naas.domain.decoder;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.kt.naas.domain.SnError;
import com.kt.naas.domain.SnResult;
import com.kt.naas.error.ErrorCodes;
import com.kt.naas.error.ExceptionFactory;

public class SnResultDecoder {
	private SnResult result;
	
	public void setResult(SnResult result)
	{
		this.result = result;
	}
	public void setError(SnError error)
	{
		result.setCode(error.getCode());
	}
	
	public SnResult parse(String str) 
	{
		Digester digester = new Digester();
		digester.setUseContextClassLoader(true);
		digester.setValidating(false);
		
		digester.addObjectCreate("*/message", SnResult.class);
		digester.addSetProperties("*/message", "status", "status");
		digester.addSetProperties("*/message", "message", "message");
		digester.addSetNext("*/message", "setResult");
		
		digester.addObjectCreate("*/error", SnError.class);
		digester.addSetProperties("*/error", "code", "code");
	 	digester.addSetProperties("*/error", "message", "message");
	 	digester.addSetNext("*/error", "setError");
	 	digester.push(this);
	 	
	 	try {
			digester.parse(new StringReader(str));
			return result;
		} catch (IOException e) {
			throw ExceptionFactory.getSNPException(ErrorCodes.EMS_MSG_DECODING_FAIL, e.getMessage());
		} catch (SAXException e) {
			throw ExceptionFactory.getSNPException(ErrorCodes.EMS_MSG_DECODING_FAIL, e.getMessage());
		}
	}
}
