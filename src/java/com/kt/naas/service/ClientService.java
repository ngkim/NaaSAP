package com.kt.naas.service;

import com.kt.naas.message.EventMessage;
import com.kt.naas.message.ResponseMessage;

public interface ClientService extends Service {
	public void responseToClient(ResponseMessage response);
	public void notifyEvent(EventMessage event);
}
