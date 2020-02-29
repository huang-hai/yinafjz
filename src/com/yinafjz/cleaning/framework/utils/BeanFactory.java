package com.yinafjz.cleaning.framework.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class BeanFactory {
	
    public BeanFactory() {

    }

    public static <T> T getInstance(HttpServletRequest request, String beanName) {
		T instace = null;
		if (null != request) {
		    WebApplicationContext ctx = RequestContextUtils
			    .getWebApplicationContext(request);
		    instace = (T) ctx.getBean(beanName);
		}
		return instace;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(String beanName) {
		T instace = null;
		instace = (T) AppContext.getInstance().getAppContext()
			.getBean(beanName);
		return instace;
    }
}
