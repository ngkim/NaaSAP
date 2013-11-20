package com.kt.naas.process.client;

public class JSONMsg {
	private String otherkey;
	private String key;
	
	// http://echo.jsontest.com/key/value/otherkey/othervalue
	/** {
	   "otherkey": "othervalue",
	   "key": "value"
	} */
	
	public JSONMsg() {
	}

	public String getOtherkey() {
		return otherkey;
	}

	public void setOtherkey(String otherkey) {
		this.otherkey = otherkey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}