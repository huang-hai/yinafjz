package com.yinafjz.cleaning.framework.utils;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

//import net.sf.json.JSONArray;

/**
 * request工具
 * 
 * 
 */
public class RequestTool {
	
	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return 请求路径
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI();
		if(request.getQueryString()!=null)
			requestPath = requestPath + "?"	+ request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length());// 去掉项目路径
		return requestPath;
	}
	/**
	 * 获得请求IP
	 * 
	 * @param request
	 * @return IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 获得请求的全路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getReuqestAllPath(HttpServletRequest request){
		Map properties = request.getParameterMap();
//		JSONArray jsonArray = JSONArray.fromObject(properties);
		return request.getRequestURI()+ JSON.toJSONString(properties);
	}
}
