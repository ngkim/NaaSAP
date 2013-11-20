package com.kt.naas.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Vector;

import com.kt.naas.process.Processor;
import com.kt.naas.process.ProcessorFactory;
import com.kt.naas.service.ExecutionService;
import com.kt.naas.snmp.TrapMessage;
import com.kt.naas.snmp.codec.SnmpCodecFactory;
import com.kt.naas.util.TimeUtils;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

public class TrapReceiver extends IoHandlerAdapter implements Service {
	private final static OID TRAPOID = new OID("1.3.6.1.6.3.1.1.4.1.0");
	protected String processorPrefix = "TRAP_";
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	public final static int DEFAULT_TRAP_PORT = 162;
	private int	trapPort = DEFAULT_TRAP_PORT;
	private ExecutionService executionService = null;
	private NioDatagramAcceptor acceptor = null;
	private ProcessorFactory processorFactory;
	
	public TrapReceiver()
	{
		
	}

	@Override
	public void start() {
		try {
			logger.info("Start Trap Service");
			init();	
		} catch (Exception e) {
			logger.error("Trap Server Init Fail", e);
			logger.error("Exit Ems Server with error");
			System.exit(-1);
		}
	}

	@Override
	public void stop() {
		logger.info("Stop Trap Service");
		acceptor.unbind();
		acceptor.dispose();
	}

	
	public void init() throws IOException
	{
		acceptor = new NioDatagramAcceptor();
		
		acceptor.setHandler(this);
		
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new SnmpCodecFactory()));
		
		DatagramSessionConfig cfg = acceptor.getSessionConfig();
		cfg.setReuseAddress(true);
		cfg.setBothIdleTime(10);
				
		
		acceptor.bind(new InetSocketAddress(trapPort));
		logger.info("Trap Server listen on " + trapPort);
	}

	@Override
    public void sessionOpened(IoSession session) {
		//logger.info("sessionOpened " + session);
    }

	@Override
    public void sessionClosed(IoSession session) {
		//logger.info("sessionClosed " + session);
    }

	@Override
    public void exceptionCaught(IoSession session, Throwable cause) {
		logger.error("exceptionCaught " + session, cause);
    }

	@Override
    public void messageReceived(IoSession session, Object message) throws Exception {
		PDU pdu = (PDU)message;
		
		logger.info("\r\n[Response Message Received from " + session.getRemoteAddress() + "] \r\n" + pdu);
		
		if (pdu.getType() != PDU.TRAP)
		{
			logger.error("Receive non-trap message from " + session);
			logger.error(""+ message);
			return;
		}
		
		String oid = findTrapOid(pdu.getVariableBindings());
		long now = System.currentTimeMillis();
		if (oid == null)
		{
			logger.error("cannot find trap-oid.\n" + message);
			return;
		}
		
		TrapMessage trap = new TrapMessage();
		InetSocketAddress peer = (InetSocketAddress)session.getRemoteAddress();
		trap.setTrapOid(oid);
		trap.setSourceIp(peer.getAddress().getHostAddress());
		trap.setSourcePort(peer.getPort());
		trap.setRecvTimeMills(now);
		trap.setRecvTime(TimeUtils.getDateTimeString(now));
		trap.setPdu(pdu);
		
		processTrap(trap);
    }
	
	
	public void processTrap(TrapMessage trap) {
    	String sOid = trap.getTrapOid();
    	OID oid = new OID(sOid);
    	Processor processor = null;
    	
    	while (oid.size() > 5)
    	{
    		String processorName = processorPrefix+oid;
    		
    		if (processorFactory.containsBeanDefinition(processorName))
    		{
    			processor = processorFactory.getProcessor(processorName);
    			break;
    		}
    		else
    		{
    			//logger.debug("##oid##" + oid);
    			oid.removeLast();
    		}
    	}
    	
    	if (processor == null)
    	{
    		logger.warn("Processor not defined for oid(" + sOid + ")");
    		return;
    	}
    	
    	logger.info("PROCESSOR::" + processor.getClass().getName());
    	
    	if (executionService == null) {
    		logger.debug("execution service not defined.");
    	} else {
    		executionService.executeProcessor(processor, trap);	
    	}
	}

	@SuppressWarnings("rawtypes")
	private String findTrapOid(Vector v)
	{
		for (int i = 0; i < v.size(); i++) {
			VariableBinding vb = (VariableBinding)v.get(i);
			if (TRAPOID.equals(vb.getOid()))
				return vb.getVariable().toString();
		}
		return null;
	}
	
	
	@Override
    public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("messageSent " + session);
    }

	public ExecutionService getExecutionService() {
		return executionService;
	}

	public void setExecutionService(ExecutionService executionService) {
		this.executionService = executionService;
	}

	public int getTrapPort() {
		return trapPort;
	}

	public void setTrapPort(int trapPort) {
		this.trapPort = trapPort;
	}

	public String getProcessorPrefix() {
		return processorPrefix;
	}

	public void setProcessorPrefix(String processorPrefix) {
		this.processorPrefix = processorPrefix;
	}

	public ProcessorFactory getProcessorFactory() {
		return processorFactory;
	}

	public void setProcessorFactory(ProcessorFactory processorFactory) {
		this.processorFactory = processorFactory;
	}

}
