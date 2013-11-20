package com.kt.naas.service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.naas.process.Job;
import com.kt.naas.process.Processor;

/**
 *
 * @author not attributable
 * @version 1.0
 */
public class ExecutionServiceProcessor implements ExecutionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected String serviceName;
    private ThreadPoolExecutor executor;

    private int	poolSize = 1;
    private long keepAliveTime = 1L;
    
    public ExecutionServiceProcessor(){
    }
    
    @Override
	public void start()
	{
    	logger.info("start " + serviceName + "...");
    	init();
    	logger.info(serviceName + " initialized.");
	}
    
    public void init()
    {
		logger.info(String.format("poolSize=%d,keepAliveTime=%d", poolSize, keepAliveTime));
        executor = new ThreadPoolExecutor(poolSize, poolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }
    
    @Override
    public void stop()
    {
		logger.info("stop " + serviceName + "...");
		((ThreadPoolExecutor)executor).shutdown();
    }

    @Override
    public void executeProcessor(Processor processor, Object message){
        Job scheduledJob = new Job(processor, message);
        //requestQueue.add(scheduledJob);
        startupWorker(scheduledJob);
    }

    private void startupWorker(Job job){
        Worker worker = new Worker(job);
//        logger.info(String.format("###corePoolSize=%d, maximumPoolSize=%d, keepAliveTime=%d, pool=%d, queue=%d"
//        		, executor.getCorePoolSize()
//        		, executor.getMaximumPoolSize()
//        		, executor.getKeepAliveTime(TimeUnit.SECONDS)
//        		, executor.getPoolSize()
//        		, executor.getQueue().size()));
        executor.execute(worker);
//        logger.info(String.format("###corePoolSize=%d, maximumPoolSize=%d, keepAliveTime=%d, pool=%d, queue=%d"
//        		, executor.getCorePoolSize()
//        		, executor.getMaximumPoolSize()
//        		, executor.getKeepAliveTime(TimeUnit.SECONDS)
//        		, executor.getPoolSize()
//        		, executor.getQueue().size()));
    }

    private class Worker implements Runnable {
    	private Job job = null;
    	public Worker(Job job)
    	{
    		this.job = job;
    	}
    	public void run(){
    		try {
    			job.execute();
            }
            catch (Throwable ex) {
            	logger.error("Exception while processing job::"+ ex, ex);
            	

//                try {
//                    Thread.sleep(1000);
//                }
//                catch (InterruptedException e1) {
//                }
            }
        }
    }
    
	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

//	public int getCorePoolSize() {
//		return corePoolSize;
//	}
//
//	public void setCorePoolSize(int corePoolSize) {
//		this.corePoolSize = corePoolSize;
//	}
//
//	public int getMaximumPoolSize() {
//		return maximumPoolSize;
//	}
//
//	public void setMaximumPoolSize(int maximumPoolSize) {
//		this.maximumPoolSize = maximumPoolSize;
//	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public int getRequestQueueSize()
	{
		return executor.getQueue().size();
	}
}
