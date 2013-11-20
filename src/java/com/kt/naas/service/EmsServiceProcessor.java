package com.kt.naas.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.EmsDao;
import com.kt.naas.domain.Ems;
import com.kt.naas.domain.SnResult;
import com.kt.naas.domain.decoder.SnResultDecoder;
import com.kt.naas.error.ErrorCodes;
import com.kt.naas.error.ExceptionFactory;

public class EmsServiceProcessor implements EmsService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Ems>	emsMap = new HashMap<String, Ems>();
	private long				emsUpdated;
	private final static int	EMSLIST_UPDATE_INTERVAL = 5 * 60 * 1000; // 5분
	
    private boolean initialized = false;
    
	@Override
	public void start() {
		logger.info("start EmsService...");
	}

	@Override
	public void stop() {
		logger.info("stop EmsService...");
	}

	@Override
	public String polling(String emsId, String taskAPI, String action, Map<String, String> params) {
		loadEms();
		
		Ems ems = emsMap.get(emsId);
		if (ems == null)
			throw ExceptionFactory.getSNPException(ErrorCodes.UNKNOWN_EMS, emsId);
		
		// SSL Context 초기화
		if (!initialized)
			init();
		
		// 암호 인코딩
		String sAuth = ems.getUserId()+":"+ems.getPasswd();
	    String sEncodedAuth = com.kt.naas.util.Base64Utils.base64Encode(sAuth.getBytes());
	    
	    String urlString = "https://" + ems.getIpAddress() + ":" + ems.getPort() + "/servlet/" + taskAPI + "?action=" + action + getParamUrl(params);
	    
		HttpsURLConnection conn = null;
		try {
			URL url = new URL(null, urlString);
			logger.debug("url=[" + urlString + "]");

			conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestProperty("Authorization", "Basic " + sEncodedAuth);
			conn.setHostnameVerifier(new newHostNameVerifier());
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestMethod("GET");
		} catch (MalformedURLException ex) {
			throw ExceptionFactory.getSNPException(ErrorCodes.EMS_COMM_FAIL, ex.getMessage());
		} catch (IOException ioexception) {
			throw ExceptionFactory.getSNPException(ErrorCodes.EMS_COMM_FAIL, ioexception.getMessage());
		}
	    
		/**
		 * Handling the response from CDSM
		 */
		
		StringBuffer response = new StringBuffer();
		String line = null;
		try {
			BufferedReader inStreamReader = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while ((line = inStreamReader.readLine()) != null) {
				response.append(line);
			}
			inStreamReader.close();
			logger.debug("Response from CDSM : \n" + response);
		} catch (IOException ioexception) {
			throw ExceptionFactory.getSNPException(ErrorCodes.EMS_COMM_FAIL, ioexception.getMessage());
		}
		
		SnResultDecoder decoder = new SnResultDecoder();
		SnResult result = decoder.parse(response.toString());
		
		if (!result.getStatus().equals("success"))
		{
			logger.error("EMS Error:::" + result);
			throw ExceptionFactory.getSNPException(ErrorCodes.EMS_PROC_ERROR, result.getCode(), result.getMessage());
		}
		
		return response.toString();
	}

	private void init()
	{
		try {        
		    SSLContext sc = SSLContext.getInstance("SSL");        
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());        
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());    
		} 
		catch (Exception e) {
			logger.error("SSL init fail." + e);
			throw ExceptionFactory.getSNPException(ErrorCodes.SSL_INIT_FAIL, e.getMessage());
		}
	}
	
	private static String getParamUrl(Map<String, String> params)
	{
		if (params == null || params.size() == 0) return "";
		
		StringBuffer buf = new StringBuffer();
		
		for (String key : params.keySet())
		{
			String value = params.get(key);
			buf.append("&").append(key).append("=").append(value);
		}
		
		return buf.toString();
	}
	
	/**
	 * Create a trust manager that does not validate certificate chains
	 */
    private static TrustManager[] trustAllCerts = new TrustManager[]{        
            new X509TrustManager() {            
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {        
                    return null;            
                }            
                public void checkClientTrusted(                
                        java.security.cert.X509Certificate[] certs, String authType) {     
                }           
                public void checkServerTrusted(                
                        java.security.cert.X509Certificate[] certs, String authType) {     
                }        
            }    
    };   
    
    private static class newHostNameVerifier implements HostnameVerifier {
        /**
         * ignore hostname checking
         */
        public boolean verify(String hostname, SSLSession session) {   
          return true;  
        }  
    } 
    
    private void loadEms()
    {
    	long now = System.currentTimeMillis();
    	if (now - emsUpdated < EMSLIST_UPDATE_INTERVAL)
    		return;
    	
    	EmsDao dao = DaoFactory.getEmsDao();
    	
    	List<Ems> emsList = dao.selectEms();
    	
    	for (Ems ems : emsList)
    	{
    		Ems loadedEms = emsMap.get(ems.getEmsId());
    		if (loadedEms == null || !ems.equals(loadedEms))
    		{
    			logger.info("EMS Loaded :: " + ems);
    			emsMap.put(ems.getEmsId(), ems);
    		}
    	}
    	emsUpdated = now;
    }
}
