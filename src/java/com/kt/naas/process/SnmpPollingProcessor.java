package com.kt.naas.process;

import com.kt.naas.domain.TargetResource;
import com.kt.naas.snmp.SnmpPeer;
import com.kt.naas.util.PropertyUtils;

public abstract class SnmpPollingProcessor extends SnmpProcessor{
	protected TargetResource	target = null;

	protected int	readTimeout = PropertyUtils.getInt("SNMP.READTIMEOUT");
	protected int	readRetry = PropertyUtils.getInt("SNMP.READRETRY");
	protected int	writeTimeout = PropertyUtils.getInt("SNMP.WRITETIMEOUT");
	protected int	writeRetry = PropertyUtils.getInt("SNMP.WRITERETRY");
	
	@Override
	protected void initSnmp() {
		peer = new SnmpPeer(target.getMasterIp(), target.getSnmpPort(), target.getCommunity(), target.getCommunity()
				, readTimeout, readRetry, writeTimeout, writeRetry);
	}

	public TargetResource getTarget() {
		return target;
	}

	public void setTarget(TargetResource target) {
		this.target = target;
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
