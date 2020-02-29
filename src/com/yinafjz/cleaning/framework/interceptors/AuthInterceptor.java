package com.yinafjz.cleaning.framework.interceptors;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yinafjz.cleaning.framework.constants.SystemKeyWord;


/**
 * 权限拦截器
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {

	private String[] allowUrls;
	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);
	
	/**
	 * 完成页面的render后调用
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
		logger.info("[afterCompletion:AuthInterceptor]");
	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
		logger.info("[postHandle:AuthInterceptor]");
	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String requestPath = request.getRequestURI();
		logger.info("[preHandle:AuthInterceptor]requestPath:"+requestPath);
		logger.info("来访ip为:"+getClientIp(request));
		//不拦截静态文件
		if (allowUrls != null && allowUrls.length > 0) {
			for (String str : allowUrls) {
				if (!requestPath.contains(str)) {
					return true;
				}
			}
		}
		
		//查询用户是否登录
		HttpSession session = request.getSession();
		if(session == null || null == session.getAttribute(SystemKeyWord.USER_KEY_CACHE)){
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print("<script type=\"text/javascript\" charset=\"UTF-8\">window.top.location.href='" + request.getContextPath() +"/index.html';</script>");
			return false; 
		}
		return true;
	}
	
	/**
	 * 鉴权失败跳转页面
	 */
	private void forward(String msg, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("error/authMsg.jsp").forward(request, response);
	}

	public String[] getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
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
