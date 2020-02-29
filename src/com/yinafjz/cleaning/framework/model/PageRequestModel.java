package com.yinafjz.cleaning.framework.model;

public class PageRequestModel extends Object implements java.io.Serializable  {
	/**
	 * 分页请求参数
	 */
	private static final long serialVersionUID = 1L;
	private int page = 1;// 当前页
	private int rows = 10;// 每页显示记录数
	private String sort = null;// 排序字段名
	private String order = "asc";// 按什么排序(asc,desc)
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "PageRequestModel [order=" + order + ", page=" + page
				+ ", rows=" + rows + ", sort=" + sort + "]";
	}
	
}