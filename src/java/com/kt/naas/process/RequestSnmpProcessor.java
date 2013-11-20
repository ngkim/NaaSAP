package com.kt.naas.process;

import com.kt.naas.GlobalConstants;
import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.EquipDao;
import com.kt.naas.db.SnmpSettingDao;
import com.kt.naas.domain.SnmpSetting;
import com.kt.naas.domain.Equip;
import com.kt.naas.error.DatatypeMismatchException;
import com.kt.naas.error.ErrorCodes;
import com.kt.naas.error.ExceptionFactory;
import com.kt.naas.error.NoMoreDataException;
import com.kt.naas.error.SNPException;
import com.kt.naas.error.SnmpException;
import com.kt.naas.error.SnmpTimeoutException;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.service.ClientService;
import com.kt.naas.snmp.SnmpPeer;
import com.kt.naas.util.PropertyUtils;

public abstract class RequestSnmpProcessor extends SnmpProcessor {
	protected Equip				equip = null;
	protected RequestMessage 	request = null;
	protected ResponseMessage 	response = null;
	protected ClientService		clientService = null;

	protected int	readTimeout = PropertyUtils.getInt("SNMP.READTIMEOUT");
	protected int	readRetry = PropertyUtils.getInt("SNMP.READRETRY");
	protected int	writeTimeout = PropertyUtils.getInt("SNMP.WRITETIMEOUT");
	protected int	writeRetry = PropertyUtils.getInt("SNMP.WRITERETRY");
	
	
	protected void checkEquip(String equipId) {
		EquipDao equipDao = DaoFactory.getEquipDao();
		try {
			equip = equipDao.selectGetEquipInfo( equipId);	
		} catch (Exception e) {
		}
		
		if (equip == null) {
			SNPException ex = ExceptionFactory.getSNPException(ErrorCodes.UNKNOWN_EQUIPID);
			throw ex;
		}
	}
	
	@Override
	protected void initSnmp() {
		SnmpSetting snmpSetting = getSnmpSetting(equip.getEmsId(), equip.getEquipId());
		
		peer = new SnmpPeer(equip.getMasterIp(), snmpSetting.getSnmpPort(), snmpSetting.getCommunity(), snmpSetting.getCommunity()
				, readTimeout, readRetry, writeTimeout, writeRetry);
	}
	
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
		} catch (SnmpTimeoutException e) {
			SNPException ex = ExceptionFactory.getSNPException(ErrorCodes.SNMP_TIMEOUT, e.getMessage()); 
			response = new ResponseMessage(request);
			response.setResultCode(ex.getErrorCode());
			response.setResultMessage(ex.getErrorMsg());
		} catch (SnmpException e) {
			SNPException ex = ExceptionFactory.getSNPException(e.getErrorStatus(), e.getMessage()); 
			response = new ResponseMessage(request);
			response.setResultCode(ex.getErrorCode());
			response.setResultMessage(ex.getErrorMsg());
		} catch (Exception e) {
			logger.error("Exception while process request.", e);
			response.setResultCode(-1);
			response.setResultMessage("UNKNOWN ERROR");
		} finally {
			stopSnmp();
			
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
	
	protected SnmpSetting getSnmpSetting(String emsId, String equipId) {
		SnmpSettingDao snmpDao = DaoFactory.getSnmpSettingDao();
		SnmpSetting snmpSetting = null;

		try {
			snmpSetting = snmpDao.selectSnmpSettingById(emsId, equipId);
		} catch (Exception e) {
		}
		
		if (snmpSetting == null) {
			try {
				snmpSetting = snmpDao.selectSnmpSettingById(emsId, "default");
			} catch (Exception e) {
			}
		}
		
		if (snmpSetting == null) {
			try {
				snmpSetting = snmpDao.selectSnmpSettingById("default", "default");
			} catch (Exception e) {
			}
		}
		
		if (snmpSetting == null) {
			snmpSetting = new SnmpSetting();
			snmpSetting.setEmsId("default");
			snmpSetting.setEquipId("default");
			snmpSetting.setSnmpPort(161);
			snmpSetting.setCommunity("public");
		}
		
		return snmpSetting;
	}
	
	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getReadRetry() {
		return readRetry;
	}

	public void setReadRetry(int readRetry) {
		this.readRetry = readRetry;
	}

	public int getWriteTimeout() {
		return writeTimeout;
	}

	public void setWriteTimeout(int writeTimeout) {
		this.writeTimeout = writeTimeout;
	}

	public int getWriteRetry() {
		return writeRetry;
	}

	public void setWriteRetry(int writeRetry) {
		this.writeRetry = writeRetry;
	}
}
