package com.kt.naas.domain.decoder;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.kt.naas.error.ErrorCodes;
import com.kt.naas.error.ExceptionFactory;

public class RouteURLDecoder {
	private String url;
	
	public void setUrl(String url)
	{
		this.url = url.toString();
	}
	
	public String parse(String str)
	{
		Digester digester = new Digester();
		digester.setUseContextClassLoader(true);
		digester.setValidating(false);
		
		digester.push(this);
		
		digester.addBeanPropertySetter("RoutedURLResponse/primaryContentRoutedURL", "url");

	 	try {
			digester.parse(new StringReader(str));
			return url;
		} catch (IOException e) {
			throw ExceptionFactory.getSNPException(ErrorCodes.EMS_MSG_DECODING_FAIL, e.getMessage());
		} catch (SAXException e) {
			throw ExceptionFactory.getSNPException(ErrorCodes.EMS_MSG_DECODING_FAIL, e.getMessage());
		}
	}
}
