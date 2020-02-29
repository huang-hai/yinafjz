package com.yinafjz.cleaning.framework.utils;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/** 
 * 获取spring容器，以访问容器中定义的其他bean 
 */  
@Component  
public class SpringContextUtil implements ApplicationContextAware,ServletContextAware {

	private static ApplicationContext applicationContext; // Spring上下文对象.静态变量,可在任何代码任何地方任何时候中取出ApplicaitonContext. 

    private static ServletContext servletContext;// 注入 系统上下文对象
    
    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     * 
     * @param applicationContext
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        System.out.println(" com.hna.hka.rmc.common.util.SpringContextUtil setApplicationContext "+applicationContext);
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象
     * 
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
    
    /**
     * 根据传入的Bean类型，获取该对象的实例
     *
     * @param clazz 传入对象类型
     * @return 返回对象实例
     * @throws BeansException 如果实例化对象失败则抛出异常
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return applicationContext.getBean(clazz);
    }

    /**
     * 功能 : 实现 ServletContextAware接口,由Spring自动注入 系统上下文对象
     * 
     **/
    public void setServletContext(ServletContext servletContext) {
        SpringContextUtil.servletContext = servletContext;
    }

    /**
     * @return ServletContext
     */
    public static ServletContext getServletContext() {
        return servletContext;
    }
}
