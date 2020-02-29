package com.yinafjz.cleaning.framework.utils;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author admin
 * 
 */
public class AppContext {
    private static AppContext instance;

    private AbstractApplicationContext appContext;

    public synchronized static AppContext getInstance() {
	if (instance == null) {
	    instance = new AppContext();
	}
	return instance;
    }

    private AppContext() {
	this.appContext = new ClassPathXmlApplicationContext(
		"classpath:applicationContext*.xml");
    }

    public AbstractApplicationContext getAppContext() {
	return appContext;
    }
}
