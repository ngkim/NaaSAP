package com.kt.naas.process;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ProcessorFactory implements ApplicationContextAware {
	private ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}

	public Processor getProcessor(String processorName)
	{
		return (Processor)ctx.getBean(processorName);
	}
	
	public boolean containsBeanDefinition(String processorName)
	{
		return ctx.containsBeanDefinition(processorName);
	}
	
	public void init()
	{
		System.out.println("###########################");
		System.out.println("INIT ProcessorFactory");
		System.out.println("###########################");
	}
}
