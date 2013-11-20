package com.kt.naas.snmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.UserTarget;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.AuthSHA;
import org.snmp4j.security.PrivAES128;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.kt.naas.domain.SnmpProfile;
import com.kt.naas.error.ErrorCodes;
import com.kt.naas.error.ExceptionFactory;
import com.kt.naas.error.SNPException;
import com.kt.naas.util.PropertyUtils;
import com.kt.naas.util.StringUtils;

public class SnmpPeer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private int 		snmpVersion;
	private Snmp		session;
	private Target		readTarget;
	private Target		writeTarget;
	private OctetString	contextName = new OctetString();
	private OctetString	contextEngineId;
	private boolean		logging = true;

	public Snmp getSession() {
		return session;
	}

	public void setSession(Snmp session) {
		this.session = session;
	}

	public Target getReadTarget() {
		return readTarget;
	}

	public void setReadTarget(Target readTarget) {
		this.readTarget = readTarget;
	}

	public Target getWriteTarget() {
		return writeTarget;
	}

	public void setWriteTarget(Target writeTarget) {
		this.writeTarget = writeTarget;
	}

	public OctetString getContextName() {
		return contextName;
	}

	public void setContextName(OctetString contextName) {
		this.contextName = contextName;
	}

	public OctetString getContextEngineId() {
		return contextEngineId;
	}

	public void setContextEngineId(OctetString contextEngineId) {
		this.contextEngineId = contextEngineId;
	}

	public int getSnmpVersion() {
		return snmpVersion;
	}

	public void setSnmpVersion(int snmpVersion) {
		this.snmpVersion = snmpVersion;
	}
	
	public boolean isLogging() {
		return logging;
	}

	public void setLogging(boolean logging) {
		this.logging = logging;
	}

	public SnmpPeer(String masterIp, int snmpPort, String readCommunity, String writeCommunity)
	{
		this(masterIp, snmpPort, readCommunity, writeCommunity
				, PropertyUtils.getInt("SNMP.READTIMEOUT")
				, PropertyUtils.getInt("SNMP.READRETRY")
				, PropertyUtils.getInt("SNMP.WRITETIMEOUT")
				, PropertyUtils.getInt("SNMP.WRITERETRY")
		);
	}
	
	public SnmpPeer(String masterIp, int snmpPort, SnmpProfile profile)
	{
		this(masterIp, snmpPort, profile
				, PropertyUtils.getInt("SNMP.READTIMEOUT")
				, PropertyUtils.getInt("SNMP.READRETRY")
				, PropertyUtils.getInt("SNMP.WRITETIMEOUT")
				, PropertyUtils.getInt("SNMP.WRITERETRY")
		);
	}
	
	// for v2
	public SnmpPeer(String masterIp, int snmpPort, String readCommunity, String writeCommunity
			, int readTimeout, int readRetry, int writeTimeout, int writeRetry)
	{
		try {
			// Snmp 积己
			AbstractTransportMapping transport;
			transport = new DefaultUdpTransportMapping();
			session = new Snmp(transport);
			
			// for snmp ver2c
			CommunityTarget rTarget = new CommunityTarget();
		    rTarget.setCommunity(new OctetString(readCommunity));
			rTarget.setAddress(new UdpAddress(masterIp + "/" + snmpPort));
			rTarget.setRetries(readRetry);
			rTarget.setTimeout(readTimeout);
			rTarget.setVersion(SnmpConstants.version2c);

			CommunityTarget wTarget = new CommunityTarget();
		    wTarget.setCommunity(new OctetString(writeCommunity));
			wTarget.setAddress(new UdpAddress(masterIp + "/" + snmpPort));
			wTarget.setRetries(writeRetry);
			wTarget.setTimeout(writeTimeout);
			wTarget.setVersion(SnmpConstants.version2c);
			
			readTarget = rTarget;
			writeTarget = wTarget;

			snmpVersion = SnmpConstants.version2c;
			
			session.listen();
		} catch (Exception e) {
			logger.error("snmp init fail.", e);
			SNPException ex = ExceptionFactory.getSNPException(ErrorCodes.SNMP_INIT_ERROR);
			throw ex;
		}
	}
	
	// for v3
	public SnmpPeer(String masterIp, int snmpPort, SnmpProfile profile
			, int readTimeout, int readRetry, int writeTimeout, int writeRetry)
	{
		try {
			// Snmp 积己
			AbstractTransportMapping transport;
			transport = new DefaultUdpTransportMapping();
			session = new Snmp(transport);
			
			// Snmp 檬扁拳
			OctetString localEngineID = new OctetString(MPv3.createLocalEngineID());
			((MPv3)session.getMessageProcessingModel(MPv3.ID)).setLocalEngineID(localEngineID.getValue());
			int engineBootCount = 0;
			
			OctetString securityName = new OctetString(profile.getSecurityusername());
			
			OID authProtocol = null;
			OctetString authPassphrase = null;
			if ("SHA".equals(profile.getAuthenticationprotocol()))
			{
				authProtocol = AuthSHA.ID;
				authPassphrase = new OctetString(profile.getAuthenticationpassword());
			}
			else if ("MD5".equals(profile.getAuthenticationprotocol()))
			{
				authProtocol = AuthMD5.ID;
				authPassphrase = new OctetString(profile.getAuthenticationpassword());
			}
			
			
	        OID privProtocol = null;
	        OctetString privPassphrase = null;
	        if ("DES".equalsIgnoreCase(profile.getPrivacyprotocol()))
	        {
	        	privProtocol = PrivDES.ID;
	        	privPassphrase = new OctetString(profile.getPrivacypassword());;
	        }
	        else if ("AES".equalsIgnoreCase(profile.getPrivacyprotocol()))
	        {
	        	privProtocol = PrivAES128.ID;
	        	privPassphrase = new OctetString(profile.getPrivacypassword());;
	        }
						
			USM usm = new USM(SecurityProtocols.getInstance(), localEngineID, engineBootCount);
			SecurityModels.getInstance().addSecurityModel(usm);
						
			UsmUser user = new UsmUser(securityName, authProtocol, authPassphrase, privProtocol, privPassphrase);
			session.getUSM().addUser(securityName, user);
			
			UserTarget rTarget = new UserTarget();
			if (authProtocol != null)
			{
				if (privProtocol != null)
					rTarget.setSecurityLevel(SecurityLevel.AUTH_PRIV);
				else
					rTarget.setSecurityLevel(SecurityLevel.AUTH_NOPRIV);
			}
			else
				rTarget.setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
			
			rTarget.setSecurityName(securityName);
			rTarget.setAddress(new UdpAddress(masterIp + "/" + snmpPort));
			rTarget.setRetries(readRetry);
			rTarget.setTimeout(readTimeout);
			rTarget.setVersion(SnmpConstants.version3);
			rTarget.setMaxSizeRequestPDU(65535);
			
			UserTarget wTarget = new UserTarget();
			if (authProtocol != null)
			{
				if (privProtocol != null)
					wTarget.setSecurityLevel(SecurityLevel.AUTH_PRIV);
				else
					wTarget.setSecurityLevel(SecurityLevel.AUTH_NOPRIV);
			}
			else
				wTarget.setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
			
			wTarget.setSecurityName(securityName);
			wTarget.setAddress(new UdpAddress(masterIp + "/" + snmpPort));
			wTarget.setRetries(writeRetry);
			wTarget.setTimeout(writeTimeout);
			wTarget.setVersion(SnmpConstants.version3);
			wTarget.setMaxSizeRequestPDU(65535);
			
			if (profile.getContextname() != null)
				contextName = new OctetString(profile.getContextname());
			if (profile.getContextengineid() != null)
				contextEngineId = OctetString.fromHexString(StringUtils.addDelimiter(profile.getContextengineid())); //new OctetString(profile.getContextengineid());
			
			readTarget = rTarget;
			writeTarget = wTarget;
			
			snmpVersion = SnmpConstants.version3;
			
			session.listen();
		} catch (Exception e) {
			logger.error("snmp init fail.", e);
			SNPException ex = ExceptionFactory.getSNPException(ErrorCodes.SNMP_INIT_ERROR);
			throw ex;
		}
	}
}
