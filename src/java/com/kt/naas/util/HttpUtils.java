package com.kt.naas.util;

import java.io.*;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpUtils {
	
	public int openStackApiGet(String objectType, Object uri, Object body, String url, String action, String xAuthToken) {
		
		int statusCode = 0;
		
		ConfigUri configUri = new ConfigUri();
		url = configUri.makeUri(objectType, uri);

		
		HttpClient client = new HttpClient();
		
		GetMethod method = new GetMethod(url);
		method.setRequestHeader("X-Auth-Token", xAuthToken);
		method.setRequestHeader("Accept", "application/json");
		
		method.setDoAuthentication(true);
		
		try {

			statusCode = client.executeMethod(method);
			
			if(statusCode == HttpStatus.SC_OK) {
				System.out.println("Success");
				System.out.println("Result : " + method.getResponseBodyAsString());
			} else {
				System.out.println("Fail : " + method.getResponseHeaders());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		
	    return statusCode;
	}
    
    public int openStackApiPost(String objectType, Object uri, Object body, String url, String action, String xAuthToken) {

    	int statusCode = 0;
    	String uriString = "";
    	String bodyString = "";    	

    	ConfigUri configUri = new ConfigUri();
    	uriString = configUri.makeUri(objectType, uri);
		
		//Instantiate an HttpClient
		HttpClient client = new HttpClient();
		
		//Instantiate a GET HTTP method
		PostMethod method = new PostMethod(uriString);
		method.setRequestHeader("X-Auth-Token", xAuthToken);
		
		
		try {

			ConfigBody configBody = new ConfigBody();
			bodyString = configBody.makeBody(objectType, body);
	
//			String jsonData = "{ " +
//			"\"server\": { " +
//			"\"flavorRef\": \"http://211.224.204.154:8774/eed154abe9f141d092b3fa020c284f92/flavors/1\", " +
//			"\"imageRef\": \"http://211.224.204.154:9292/eed154abe9f141d092b3fa020c284f92/images/ff777a8e-83cb-41bc-8d21-fdee2e1231d1\", " +
//			"\"name\": \"openstack_test_cache2\" " +
//			"} " +
//			"}";
//			
//			System.out.println(">> jsonData : " + jsonData);
				
			// content in java
			method.setRequestEntity(new StringRequestEntity(bodyString));
	
			method.setRequestHeader("Content-type",	"application/json");
			method.setRequestHeader("Accept", "application/json");
	

			statusCode = client.executeMethod(method);
			

			// Request Header
			System.out.println(">> " + method.getName() + " " + method.getPath() + " " + method.getEffectiveVersion());
			
			Header[] requestheaders = method.getRequestHeaders();
			for(int i = 0; i < requestheaders.length; i++) {
				System.out.print(">> " + requestheaders[i]);
			}
			
			
			// Response Header
			System.out.println("<< "	+ method.getStatusLine());
			
			Header[] responseheaders = method.getResponseHeaders();
			for(int i = 0; i < responseheaders.length; i++) {
				System.out.print(">> " + responseheaders[i]);
			}
			System.out.println("");
			
			// Response Body
			System.out.println(method.getResponseBodyAsString());
			
			
			//release connection
			method.releaseConnection();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return statusCode;
    }

    

    
    public int openStackApiDelete(String url, String action, String ID, String xAuthToken) {

    	int statusCode = 0;

		//Instantiate an HttpClient
		HttpClient client = new HttpClient();
		
		//Instantiate a GET HTTP method
		DeleteMethod method = new DeleteMethod(url);
		method.setRequestHeader("X-Auth-Token", xAuthToken);
		
		
		try {
			statusCode = client.executeMethod(method);
			

			// Request Header
			System.out.println(">> " + method.getName() + " " + method.getPath() + " " + method.getEffectiveVersion());
			
			Header[] requestheaders = method.getRequestHeaders();
			for(int i = 0; i < requestheaders.length; i++) {
				System.out.print(">> " + requestheaders[i]);
			}
			
			
			// Response Header
			System.out.println("<< "	+ method.getStatusLine());
			
			Header[] responseheaders = method.getResponseHeaders();
			for(int i = 0; i < responseheaders.length; i++) {
				System.out.print(">> " + responseheaders[i]);
			}
			System.out.println("");
			
			// Response Body
			System.out.println(method.getResponseBodyAsString());
			
			
			//release connection
			method.releaseConnection();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return statusCode;
    }
	
	/**
     * HTTP Request (POST ���)
     *
     * @param url : Target Host
     * @param type : Operation to perform on whitelist entry
     * @param destination : Server IP address
     * @param port : Port number of HTTP, HTTPs
     */
    public int whitelist(String url, String type, String destination, String port) {

    	int statusCode = 0;
		//Instantiate an HttpClient
		HttpClient client = new HttpClient();
				
		//Instantiate a GET HTTP method
		PostMethod method = new PostMethod(url);
		//method.setRequestHeader("Content-type",	"text/xml; charset=ISO-8859-1");
		method.setRequestHeader("Content-type",	"application/x-www-form-urlencoded;");

		//
		//Define name-value pairs to set into the QueryString : type=Add&destination=74.125.224.72&port=80
		//		
		NameValuePair[] nvps = new NameValuePair[] {
	            new NameValuePair("type", type),
	            new NameValuePair("destination", destination),
	            new NameValuePair("port", port)
	    }; 
		//method.setQueryString(nvps); // URL�� �ٿ���
		method.setRequestBody(nvps); // Body�� �־

		try{
			statusCode = client.executeMethod(method);
			
			// Request Header
			System.out.println(">> " + method.getName() + " " + method.getPath() + " " + method.getEffectiveVersion());
			
			Header[] requestheaders = method.getRequestHeaders();
			for(int i = 0; i < requestheaders.length; i++) {
				System.out.print(">> " + requestheaders[i]);
			}
			
			// Request Body
			for(int i = 0; i < nvps.length; i++) {
				//System.out.println(">> " + nvps[i]);
				if(i == 0)
					System.out.print("\n" + nvps[i].getName() + "=" + nvps[i].getValue());
				else
					System.out.print("&" + nvps[i].getName() + "=" + nvps[i].getValue());
			}
			System.out.println("\n");
			
			// Response Header
			System.out.println("<< "	+ method.getStatusLine());
			
			Header[] responseheaders = method.getResponseHeaders();
			for(int i = 0; i < responseheaders.length; i++) {
				System.out.print(">> " + responseheaders[i]);
			}
			System.out.println("");
			
			// Response Body
			System.out.println(method.getResponseBodyAsString());

			//release connection
			method.releaseConnection();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return statusCode;
    }
    
    /**
     * HTTP Request (POST ���)
     *
     * @param url : Target Host
     * @param origin : Host name in the URL
     * @param object : Path and file name in the URL
     * @param port : Port number of HTTP, HTTPs
     * @param execTime : When to perform purge
     */
    public int purge(String url, String origin, String object, String port, String execTime) {
    	
    	int statusCode = 0;
		//Instantiate an HttpClient
		HttpClient client = new HttpClient();
				
		//Instantiate a GET HTTP method
		PostMethod method = new PostMethod(url);
		method.setRequestHeader("Content-type",	"application/x-www-form-urlencoded;");

		//
		//Define name-value pairs to set into the QueryString : type=Add&destination=74.125.224.72&port=80
		//
		
		NameValuePair[] nvps = new NameValuePair[] {
	            new NameValuePair("origin", origin),
	            new NameValuePair("object", object),
	            new NameValuePair("port", port),
	            new NameValuePair("executiontime", execTime)
	    };
		
		//method.setQueryString(nvps); // URL�� �ٿ���
		method.setRequestBody(nvps); // Body�� �־

		try{
			statusCode = client.executeMethod(method);

			System.out.println("Status Line>>>"	+ method.getStatusLine());
			System.out.println("Status Code = " + statusCode);
			System.out.println("Status Text>>>"	+ HttpStatus.getStatusText(statusCode));
			//System.out.println("QueryString>>> " + method.getQueryString());
			//System.out.println("QueryString(decoding)>>> " + URIUtil.decode(method.getQueryString(), "UTF-8"));
			
			System.out.println("=== Response Header ===");
			Header[] rep = method.getResponseHeaders("Date");
			String dateString = rep[0].getValue();			
			System.out.println("= Date >>> " + dateString);
			
			rep = method.getResponseHeaders("Server");
			String serverName = rep[0].getValue();
			System.out.println("= Server >>> " + serverName);
			
			rep = method.getResponseHeaders("Connection");
			String connection = rep[0].getValue();
			System.out.println("= Connection >>> " + connection);
			System.out.println("========================");

			//Get data as a String
			System.out.println(method.getResponseBodyAsString());
			
			//release connection
			method.releaseConnection();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return statusCode;
    }
    
    /**
     * HTTP Request (POST ���)
     *
     * @param url : Target Host
     * @param origin : Host name in the URL
     * @param object : Path and file name in the URL
     * @param port : Port number of HTTP, HTTPs
     * @param expire : Expiration Date. Time
     */
    public int preload(String url, String origin, String object, String port, String expire) {

    	int statusCode = 0;
		//Instantiate an HttpClient
		HttpClient client = new HttpClient();
				
		//Instantiate a GET HTTP method
		PostMethod method = new PostMethod(url);
		method.setRequestHeader("Content-type",	"application/x-www-form-urlencoded;");

		//
		//Define name-value pairs to set into the QueryString : type=Add&destination=74.125.224.72&port=80
		//
		
		NameValuePair[] nvps = new NameValuePair[] {
	            new NameValuePair("origin", origin),
	            new NameValuePair("object", object),
	            new NameValuePair("port", port),
	            new NameValuePair("expire", expire)
	    };
		
		//method.setQueryString(nvps); // URL�� �ٿ���
		method.setRequestBody(nvps); // Body�� �־

		try{
			statusCode = client.executeMethod(method);

			System.out.println("Status Line>>>"	+ method.getStatusLine());
			System.out.println("Status Code = " + statusCode);
			System.out.println("Status Text>>>"	+ HttpStatus.getStatusText(statusCode));
			//System.out.println("QueryString>>> " + method.getQueryString());
			//System.out.println("QueryString(decoding)>>> " + URIUtil.decode(method.getQueryString(), "UTF-8"));
			
			System.out.println("=== Response Header ===");
			Header[] rep = method.getResponseHeaders("Date");
			String dateString = rep[0].getValue();			
			System.out.println("= Date >>> " + dateString);
			
			rep = method.getResponseHeaders("Server");
			String serverName = rep[0].getValue();
			System.out.println("= Server >>> " + serverName);
			
			rep = method.getResponseHeaders("Connection");
			String connection = rep[0].getValue();
			System.out.println("= Connection >>> " + connection);
			System.out.println("========================");

			//Get data as a String
			System.out.println(method.getResponseBodyAsString());

			//release connection
			method.releaseConnection();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return statusCode;
    }
    
}
