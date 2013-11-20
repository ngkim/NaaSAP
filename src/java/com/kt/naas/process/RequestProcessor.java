package com.kt.naas.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.naas.GlobalConstants;
import com.kt.naas.error.DatatypeMismatchException;
import com.kt.naas.error.ErrorCodes;
import com.kt.naas.error.ExceptionFactory;
import com.kt.naas.error.NoMoreDataException;
import com.kt.naas.error.SNPException;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.service.ClientService;

public abstract class RequestProcessor implements Processor {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected RequestMessage 	request = null;
	protected ResponseMessage 	response = null;
	protected ClientService		clientService = null;

	@Override
	public void process(Object message) {
		request = (RequestMessage)message;
		response = new ResponseMessage(request);
		long startTime = System.currentTimeMillis();
		
		logger.info("received message from client\n" + request);
		
		try {
			processRequest();
		} catch (SNPException e) {
			response = new ResponseMessage(request);
			response.setResultCode(e.getErrorCode());
			response.setResultMessage(e.getErrorMsg());
		} catch (NoMoreDataException e) {
			SNPException ex = ExceptionFactory.getSNPException(ErrorCodes.NO_MORE_DATA, e.getMessage()); 
			response = new ResponseMessage(request);
			response.setResultCode(ex.getErrorCode());
			response.setResultMessage(ex.getErrorMsg());
		} catch (DatatypeMismatchException e) {
			SNPException ex = ExceptionFactory.getSNPException(ErrorCodes.DATATYPE_MISMATCH, e.getMessage()); 
			response = new ResponseMessage(request);
			response.setResultCode(ex.getErrorCode());
			response.setResultMessage(ex.getErrorMsg());
		} catch (Exception e) {
			logger.error("Exception while process request.", e);
			response.setResultCode(-1);
			response.setResultMessage("UNKNOWN ERROR");
		} finally {
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			logger.debug("service end...[" + elapsedTime +"ms]");
			// TODO DB로깅(작업이력) 추가
		}
		
		if (request.getSenderType().equals(GlobalConstants.ACTOR_WEBUI)
				|| request.getSenderType().equals(GlobalConstants.ACTOR_WEBSVC))
		{
			logger.info("send message to client/nbi\n" + response);
			clientService.responseToClient(response);
		}
	}

	public abstract void processRequest();

	public RequestMessage getRequest() {
		return request;
	}

	public void setRequest(RequestMessage request) {
		this.request = request;
	}

	public ResponseMessage getResponse() {
		return response;
	}

	public void setResponse(ResponseMessage response) {
		this.response = response;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
}
