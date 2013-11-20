package com.kt.naas.domain;

import com.kt.naas.domain.SnObject;

public class SnEntry extends SnObject {
	private static final long serialVersionUID = 2369708475073570289L;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("SnEntry [id=%s]", id);
	}
}
