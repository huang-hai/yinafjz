package com.yinafjz.cleaning.framework.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.yinafjz.cleaning.framework.exception.YinafjzException;
import com.yinafjz.cleaning.framework.model.ResultModel;


public class ResponseTool {
	
	private final static Log log = LogFactory.getLog(ResponseTool.class);
	public static void responseJsonFormStr(String str,HttpServletRequest request, HttpServletResponse response){
		String dataType = "text/json;charset=UTF-8";
		responseJsonFormStr(str, dataType, request, response);
	}
	
	/**
	 * 将信息输出到前端
	 * @param obj
	 * @param request
	 * @param response
	 */
	public static void responseJson(Object obj,HttpServletRequest request, HttpServletResponse response){
		String json =getJson(obj);
		responseJsonFormStr(json, request, response);
	}
	
	/**
	 * 重定向跳转
	 * @param url
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public static void sendRedirect(String url,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect(url);
	}
	
	public static void print(String message, OutputStream os) throws Exception {
		OutputStreamWriter out = new OutputStreamWriter(os, "utf-8");
		out.write(message);
		out.flush();
		out.close();
	}
	 /**
	  * 返回值
	  * @param object
	  * @param dataType
	  */
	 public static String getJson(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		String json = "";
		json = jsonArray.toString();
		if (json.indexOf("[")==0) {
			json = json.substring(1,json.length()-1);
		}
		return json;
	}
	
	/**
	 * 将json数据传到前台
	 */
	 private static void responseJsonFormStr(String str,String dataType,HttpServletRequest request, HttpServletResponse response){
			if (dataType.length()<=0) {
				dataType = "text/json;charset=UTF-8";
			}
			String cb = request.getParameter("callback");
			if (cb!=null) {
				StringBuffer sb = new StringBuffer(cb);
				sb.append("(");
				sb.append(str);
				sb.append(")");
				str = sb.toString();				
			}
			log.info("手机客户端请求返回值　："+str);
			response.setContentType(dataType);
			try {
				response.addHeader("Access-Control-Allow-Origin", "*");
				response.getWriter().write(str);
			} catch (IOException e) {
				log.error("responseJson failed:",e);
			}
//			getRequestParams(request, req_time, str);
	 }
	 
	@SuppressWarnings("unchecked")
	private static void getRequestParams(HttpServletRequest request,long req_time,String str){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			 Map<String,Object> mapRes = new HashMap<String,Object>();
			 Enumeration enu = request.getParameterNames() ;
			 while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				map.put(name, request.getParameter(name));
			}
			mapRes.put("url",RequestTool. getRequestPath(request));
			mapRes.put("ip", RequestTool.getIpAddr(request));
			mapRes.put("time", System.currentTimeMillis() - req_time );
//			mapRes.put("response", str);
//			mapRes.put("request", map);
//			log.info("url:"+RequestTool. getRequestPath(request));
//			log.info("time"+(System.currentTimeMillis() - req_time));
//			log.info("request:"+request.getParameter(name));
//			WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
//			RedisTemplate redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");	
//			redisTemplate.opsForList().leftPush(REDIS_LOG_KEY,mapRes);
		} catch (Exception e) {
			log.error("add redis error:"+e.getMessage());
		}
	 }
	 
	 public static void exceptionReturn(YinafjzException e,HttpServletRequest request, HttpServletResponse response,Logger logger) {
		if (e.getMessage().isEmpty()) {
			log.warn(e.getMsg());
		} else {
			log.error(e.getMessage(), e);
		}
		Object obj = MessageTool.getErrorResultModel(e.getMsg(), e.getCode());
		ResponseTool.responseJson(obj, request, response);
	}
	public  static ResultModel handlerException(Exception e,Logger logger){
		 if(e instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){
			 ResultModel result = MessageTool.getErrorResultModel(MessageTool.getMessage("COMMON_UPLOAD_FILT_MAX_SIZE_ERROR"),MessageTool.getMessage("COMMON_UPLOAD_FILT_MAX_SIZE_ERROR_CODE"));
			 return result;
		 }
		 //log.info("[handlerException:MemberController]异常类型："+e.getClass().getSimpleName());
		 log.error(e.getMessage(), e);
		ResultModel result = MessageTool.getErrorResultModel(MessageTool.getMessage("COMMON_SYSTEM_ERROR"),MessageTool.getMessage("COMMON_SYSTEM_ERROR_CODE"));
		return result;
	}
}
