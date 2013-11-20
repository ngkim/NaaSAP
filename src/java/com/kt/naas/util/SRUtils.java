package com.kt.naas.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.naas.domain.decoder.RouteURLDecoder;
import com.kt.naas.error.ErrorCodes;
import com.kt.naas.error.ExceptionFactory;

public class SRUtils {
	protected final static Logger logger = LoggerFactory.getLogger(SRUtils.class);
	
	public static String routeURL(String srIp, String cdnURL, String clientIp)
	{
		String url = String.format("http://%s/routeURL?CDNURL=%s&ClientIP=%s", srIp, cdnURL, clientIp);
		
		logger.debug("url=" + url);
		BufferedReader br = null;
		StringBuffer response = new StringBuffer();
		String line = null;
		
		try {
			br = new BufferedReader(new InputStreamReader((new URL(url)).openConnection().getInputStream()));
			
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();
			
			logger.debug("Response from SR : \n" + response);
		} catch (MalformedURLException ex) {
			throw ExceptionFactory.getSNPException(ErrorCodes.SR_COMM_FAIL, ex.getMessage());
		} catch (FileNotFoundException e) {
			logger.debug("ERROR 404::" + cdnURL);
			return null;
		} catch (IOException ioexception) {
			logger.error("routeURL fail::" + ioexception);
			throw ExceptionFactory.getSNPException(ErrorCodes.SR_COMM_FAIL, ioexception.getMessage());
		} finally {
			if (br != null)
			{
				try {
					br.close();
				} catch (Exception e) {
				}
			}
		}
		
		RouteURLDecoder decoder = new RouteURLDecoder();
		String result = decoder.parse(response.toString());
		
		return result;
	}
}
