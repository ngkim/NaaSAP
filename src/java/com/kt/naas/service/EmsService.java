package com.kt.naas.service;

import java.util.Map;

public interface EmsService extends Service {
	public String polling(String emsId, String taskAPI, String action, Map<String, String> params);
}
