package com.kt.naas.domain;

import com.kt.naas.domain.SnObject;

public class SnResult extends SnObject {
	private static final long serialVersionUID = -4701540914125426668L;

	private String status;
	private String code;
	private String message;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return String.format("SnResult [status=%s, code=%s, message=%s]",
				status, code, message);
	}
}
