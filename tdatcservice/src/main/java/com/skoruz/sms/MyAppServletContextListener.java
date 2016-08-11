package com.skoruz.sms;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MyAppServletContextListener implements ServletContextListener{
	
	
	public static long checkAfterScheduleTime = 15*60*1000; // 3 minutes
	
	 ApplicationContext context;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		System.out.println("The sms updating service has been started..");
		
		 context = new ClassPathXmlApplicationContext("spring.xml");
		 
		 
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("The sms updating service has been destroyed..");
	}

}
