package com.yinafjz.cleaning.framework.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.yinafjz.cleaning.framework.constants.SystemKeyWord;
import com.yinafjz.cleaning.framework.utils.BeanFactory;
import com.yinafjz.cleaning.framework.utils.DateUtils;

public abstract class AHandleModelAndView {
	
	private static final Logger logger = LoggerFactory.getLogger(AHandleModelAndView.class);
	
//    @Autowired
//    @Qualifier("syLogInfoServiceImpl")
//    private SyLogInfoService syLogInfoService;
	
	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	protected ModelAndView dispatchDefMv(JSONObject json) {
		ModelAndView mv = new ModelAndView();
		String viewName = SystemKeyWord.JSON_VIEW;
		mv.setViewName(viewName);
		mv.addObject(SystemKeyWord.JSON_NAME, json.toString());
		return mv;
    }

    protected ModelAndView dispatchDefMv(String content) {
		ModelAndView mv = new ModelAndView();
		String viewName = SystemKeyWord.JSON_VIEW;
		mv.setViewName(viewName);
		mv.addObject(SystemKeyWord.JSON_NAME, content);
		return mv;
    }

    protected ModelAndView dispatchDefMv(String viewName, Object content) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(viewName);
		mv.addObject(SystemKeyWord.JSON_NAME, content);
		return mv;
    }

    protected String annotateOfClsName(Class<?> clazz) {
    	return DateUtils.annotateOfClsName(clazz);
    }
    
    public static String getCurrentMethodName() {
		StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
		String fileName = traceElement.getClassName();
		String methodName = traceElement.getMethodName();
		return fileName + "." + methodName;
    }

    protected <T> T getInstance(String beanName) {
    	return BeanFactory.getInstance(beanName);
    }

//    protected SyLogInfoService getLogInfoService() {
//    	return this.syLogInfoService;
//    }

//    protected void saveLogInfo(SyLogInfo logInfo) {
//		try {
//		    getLogInfoService().save(logInfo);
//		} catch (Exception e) {
//		    System.out.println(e);
//		    logger.debug(e.getMessage());
//		}
//    }

//    protected void saveLogInfo(HttpServletRequest request,
//	    Map<String, String> singleMap) {
//		try {
//		    StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
//		    String fileName = traceElement.getClassName();
//		    String methodName = traceElement.getMethodName();
//		    String fullMethodName = fileName + "." + methodName;
//		    SyLogInfo logInfo = new SyLogInfo();
//		    SyAdminUser currentUser = getCurrentUser(request);
//		    if (currentUser != null)
//			logInfo.setLogUserId(currentUser.getUserId());// 当前用户
//		    logInfo.setLogAction(fullMethodName);// 当前action
//		    logInfo.setLogTime(DateUtil.currentTimestamp());// 当前系统时间
//		    logInfo.setLogIp(getClientIp(request));// 当前客户端ip
//		    logInfo.setLogData(JSONObject.fromObject(singleMap).toString());
//		    saveLogInfo(logInfo);
//		} catch (Exception e) {
//		    System.out.println(e);
//		    logger.debug(e.getMessage());
//		}
//    }

    
    /**
	 * 获取接口参数
	 * @param request
	 * @return
	 */
	public static Map<String,String> paramsMap(HttpServletRequest request){
		Map<String,String> resultMap = new HashMap<String, String>();
		Map<String, String[]> map = request.getParameterMap();  
		Set<Entry<String, String[]>> set = map.entrySet();  
        Iterator<Entry<String, String[]>> it = set.iterator();  
        while (it.hasNext()) {  
            Entry<String, String[]> entry = it.next();  
            resultMap.put(entry.getKey(), entry.getValue()[0]);
        }  
		return resultMap;
	}

    /*
     * 取客户端ip
     */
    public String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getRemoteAddr();
		}
		return ip;
    }

}
