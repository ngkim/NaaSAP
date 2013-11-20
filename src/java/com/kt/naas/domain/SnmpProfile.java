package com.kt.naas.domain;

import java.io.Serializable;

import com.kt.naas.GlobalConstants;

public class SnmpProfile implements GlobalConstants, Serializable {

	private static final long serialVersionUID = -961961768106200122L;
	
	private String snmpprofilename;
	private String securityusername;
	private String contextname;
	private String contextengineid;
	private String authenticationprotocol;
	private String authenticationpassword;
	private String privacyprotocol;
	private String privacypassword;
	
	
	public String getSnmpprofilename() {
		return snmpprofilename;
	}
	public void setSnmpprofilename(String snmpprofilename) {
		this.snmpprofilename = snmpprofilename;
	}
	
	public String getSecurityusername() {
		return securityusername;
	}
	public void setSecurityusername(String securityusername) {
		this.securityusername = securityusername;
	}
	
	public String getContextname() {
		return contextname;
	}
	public void setContextname(String contextname) {
		this.contextname = contextname;
	}
	
	public String getContextengineid() {
		return contextengineid;
	}
	public void setContextengineid(String contextengineid) {
		this.contextengineid = contextengineid;
	}
	
	public String getAuthenticationprotocol() {
		return authenticationprotocol;
	}
	public void setAuthenticationprotocol(String authenticationprotocol) {
		this.authenticationprotocol = authenticationprotocol;
	}
	
	public String getAuthenticationpassword() {
		return authenticationpassword;
	}
	public void setAuthenticationpassword(String authenticationpassword) {
		this.authenticationpassword = authenticationpassword;
	}
	
	public String getPrivacyprotocol() {
		return privacyprotocol;
	}
	public void setPrivacyprotocol(String privacyprotocol) {
		this.privacyprotocol = privacyprotocol;
	}
	
	public String getPrivacypassword() {
		return privacypassword;
	}
	public void setPrivacypassword(String privacypassword) {
		this.privacypassword = privacypassword;
	}
	
	@Override
	public String toString() {
		return String.format("SnmpProfile [authenticationpassword=%s, authenticationprotocol=%s, contextengineid=%s, contextname=%s, privacypassword=%s, privacyprotocol=%s, securityusername=%s, snmpprofilename=%s]",
						authenticationpassword, authenticationprotocol, contextengineid, contextname, privacypassword, privacyprotocol, securityusername, snmpprofilename);
	}
	
}
