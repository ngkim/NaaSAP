package com.kt.naas.domain;

import com.kt.naas.domain.SnObject;

public class SnError extends SnObject {
	private static final long serialVersionUID = -7175195355378040316L;

	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("SnError [code=%s, message=%s]", code, message);
	}
}
