package com.kt.naas.snmp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeListener;

public class MyTreeListener implements TreeListener 
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private boolean finished;
	private TreeEvent finishEvent;
	private int reqCounts = 0;
	private int objCounts = 0;
	private long startTime = System.currentTimeMillis();
	
	boolean requestLogging	= true;
	boolean responseLogging = true;
	boolean summaryLogging = true;
	
	private List<VariableBinding> varList = new ArrayList<VariableBinding>();
	
	public TreeEvent getFinishEvent() {
		return finishEvent;
	}

	public List<VariableBinding> getVarList() {
		return varList;
	}

	
	public boolean next(TreeEvent e) {
		reqCounts++;
		if (e.getVariableBindings() != null) {
			VariableBinding[] vbs = e.getVariableBindings();
			objCounts += vbs.length;
			for (int i = 0; i < vbs.length; i++) {
				varList.add(vbs[i]);
				if (responseLogging)
					logger.debug("[SNMP]" + vbs[i].toString());
			}
		}
//		try {
//			Thread.sleep(10);
//		} catch (Exception e2) {
//			// do nothing
//		}
		return true;
	}

	public boolean isFinished() {
	    return finished;
	}
	
	public void finished(TreeEvent e) {
		if ((e.getVariableBindings() != null)
				&& (e.getVariableBindings().length > 0)) {
			next(e);
		}
		if (summaryLogging)
		{
			logger.debug("[SNMP]Total requests sent:    " + reqCounts);
			logger.debug("[SNMP]Total objects received: " + objCounts);
			logger.debug("[SNMP]Total walk time:        "
			+ (System.currentTimeMillis() - startTime)
			+ " milliseconds");
		}

	    finished = true;
	    finishEvent = e;
		synchronized (this) {
			this.notify();
		}
	}

	public boolean isRequestLogging() {
		return requestLogging;
	}

	public void setRequestLogging(boolean requestLogging) {
		this.requestLogging = requestLogging;
	}

	public boolean isResponseLogging() {
		return responseLogging;
	}

	public void setResponseLogging(boolean responseLogging) {
		this.responseLogging = responseLogging;
	}

	public boolean isSummaryLogging() {
		return summaryLogging;
	}

	public void setSummaryLogging(boolean summaryLogging) {
		this.summaryLogging = summaryLogging;
	}
}

