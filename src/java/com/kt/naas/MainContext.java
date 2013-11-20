package com.kt.naas;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MainContext implements ApplicationContextAware {
	private static ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext apctx) throws BeansException {
		ctx = apctx;
	}

	public static ApplicationContext getApplicationContext()
	{
		return ctx;
	}
	
	public static Object getBean(String name)
	{
		if (ctx == null)
			return null;
		else 
			return ctx.getBean(name);
	}
}
