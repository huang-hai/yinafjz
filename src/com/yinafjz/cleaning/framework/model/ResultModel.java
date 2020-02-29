/**   
* @Title: ResultModel.java  
* @Package com.yinafjz.cleaning.user.model  
* @Description: TODO(用一句话描述该文件做什么)  
* @author Huanghai 
* @date 2018-12-4 上午9:49:58  
* @version V1.0   
*/ 
package com.yinafjz.cleaning.framework.model;

import java.io.Serializable;

/**  
 * @ClassName: ResultModel  
 * @Description: TODO(统一返回实体)  
 * @author Huanghai
 * @date 2018-12-4 上午9:49:58  
 *   
 */
public class ResultModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8220580562597745578L;
	
	private boolean success = true; //是否成功 默认是
	private String msg = ""; //消息
	private String code = "200"; //状态码 默认200
	private Object obj = null; //返回对象
	
	/**
	 * 状态码：成功
	 */
	public static String STATIC_CODE_SUCCESS = "200";
	/**
	 * 状态码：失败
	 */
	public static String STATIC_CODE_FAILURE = "500";
	
	
	/**
	 * 
	 */
	public ResultModel() {
		super();
	}

	/**
	 * @param success
	 * @param msg
	 * @param code
	 */
	public ResultModel(boolean success, String msg, String code) {
		super();
		this.success = success;
		this.msg = msg;
		this.code = code;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "ResultModel [success=" + success + ", msg=" + msg + ", code="
				+ code + ", obj=" + obj + "]";
	}
	
}
