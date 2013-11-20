package com.kt.naas.process.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.naas.db.DaoFactory;
import com.kt.naas.db.EmsDao;
import com.kt.naas.db.ServerDao;
import com.kt.naas.domain.Ems;
import com.kt.naas.domain.Server;
import com.kt.naas.process.Processor;
import com.kt.naas.process.ProcessorFactory;
import com.kt.naas.service.ExecutionService;

public class AuditLauncher {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private ExecutionService executor;
	private ProcessorFactory factory;

	public void run()
	{
		logger.info("======== Audit =========");
		try {
			
			ServerDao serverDao = DaoFactory.getServerDao();
			List<Server> servers = serverDao.selectServer();
			System.out.println("=== servers : " + servers);
			
			for (Server server : servers)
			{
				System.out.println("=== server : " + server);
				
				if(server.getStatus().equals("Y") && server.getServicetype().equals("AWS"))
					auditAwsResource(server);
			}

			
		} catch (Exception e) {
			logger.error("Exception in AuditLauncher", e);
		}
	}
	
	
	
	private void auditAwsResource(Server server)
	{
		Processor processor = factory.getProcessor("auditAwsResourceProcessor");
		executor.executeProcessor(processor, server);
	}
	
	
	
	
	
	
	
	
	
	private void auditSnLocation(Ems ems)
	{
		Processor processor = factory.getProcessor("auditSnLocationProcessor");
		executor.executeProcessor(processor, ems);
	}
	
	private void auditSnEquip(Ems ems)
	{
		Processor processor = factory.getProcessor("auditSnEquipProcessor");
		executor.executeProcessor(processor, ems);
	}
	
	private void auditSnService(Ems ems)
	{
		Processor processor = factory.getProcessor("auditSnServiceProcessor");
		executor.executeProcessor(processor, ems);
	}
	
	private void auditSnServiceRepStatus(Ems ems)
	{
		Processor processor = factory.getProcessor("auditSnServiceRepStatusProcessor");
		executor.executeProcessor(processor, ems);
	}
	
	private void auditSnSeOfServiceRepStatus(Ems ems)
	{
		Processor processor = factory.getProcessor("auditSnSeOfServiceRepStatusProcessor");
		executor.executeProcessor(processor, ems);
	}
	
	public ExecutionService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutionService executor) {
		this.executor = executor;
	}

	public ProcessorFactory getFactory() {
		return factory;
	}

	public void setFactory(ProcessorFactory factory) {
		this.factory = factory;
	}

}
