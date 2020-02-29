package com.yinafjz.cleaning.framework.model;

import java.util.List;

public class PageResultModel implements java.io.Serializable {
	
	/**
	 * 分页响应
	 */
	private static final long serialVersionUID = 1L;
	private boolean success = false;// 是否成功
	private String msg = "";		// 提示信息
	private String code=""; 		//错误代码
	private PagerModel pager;
	private List rows;				// 每行记录
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
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
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public PagerModel getPager() {
		return pager;
	}
	public void setPager(PagerModel pager) {
		this.pager = pager;
	}
	@Override
	public String toString() {
		return "PageResultModel [code=" + code + ", msg=" + msg + ", pager="
				+ pager + ", rows=" + rows + ", success=" + success + "]";
	}
	
}
