package com.kt.naas.service;

import com.kt.naas.process.Processor;

public interface ExecutionService extends Service {
	public void executeProcessor(Processor processor, Object message);
}