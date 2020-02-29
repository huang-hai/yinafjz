package com.yinafjz.cleaning.framework.model;

import java.io.Serializable;
public class PagerModel  extends Object implements Serializable {
	private static final long serialVersionUID = 1L;
	private int totalRows;//总记录数
	private int pageSize = 10;//每页条数
	private int currentPage;//当前页
	private int totalPages;//总页数
	private int startRow;
	private int endRow;
	private boolean isGetCount = true;

	public boolean isGetCount() {
		return this.isGetCount;
	}

	public void setGetCount(boolean isGetCount) {
		this.isGetCount = isGetCount;
	}

	public PagerModel() {
	}

	public PagerModel(int pageSize, int currentPage) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}

	public PagerModel(int pageSize, int currentPage, int totalRows) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalRows = totalRows;
		calTotalPages();
		this.isGetCount = false;
	}

	public PagerModel(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getStartRow() {
		return this.startRow;
	}

	public int getEndRow() {
		return this.endRow;
	}
	
	public int getTotalPages() {
		return this.totalPages;
	}

	private void calTotalPages() {
		this.totalPages = ((this.totalRows % this.pageSize == 0) ? this.totalRows
				/ this.pageSize
				: this.totalRows / this.pageSize + 1);
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		calTotalPages();
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	
	public void setEndRow(int endRow){
		this.endRow = endRow;
	}
	

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setCurrentPage(int currentPage) {
		currentPage = (currentPage < 1) ? 1 : currentPage;
		currentPage = (currentPage > this.totalPages) ? this.totalPages
				: currentPage;
//		this.startRow = ((currentPage - 1) * this.pageSize) + 1;
		this.startRow = ((currentPage - 1) * this.pageSize);
		this.startRow = ((this.startRow < 0) ? 0 : this.startRow);
		this.endRow = currentPage * this.pageSize;
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRows() {
		return this.totalRows;
	}

	public void first() {
		this.currentPage = 1;
		this.startRow = 0;
	}

	public void previous() {
		if (this.currentPage == 1) {
			return;
		}
		this.currentPage -= 1;
		this.startRow = ((this.currentPage - 1) * this.pageSize);
	}

	public void next() {
		if (this.currentPage < this.totalPages) {
			this.currentPage += 1;
		}
		this.startRow = ((this.currentPage - 1) * this.pageSize);
	}

	public void last() {
		this.currentPage = this.totalPages;
		this.startRow = ((this.currentPage - 1) * this.pageSize);
	}

	public void refresh(int _currentPage) {
		this.currentPage = _currentPage;
		if (this.currentPage > this.totalPages)
			last();
	}

	public boolean isLastPage() {
		return (this.currentPage != this.totalPages);
	}

	public boolean isFirstPage() {
		return (this.currentPage != 1);
	}

	@Override
	public String toString() {
		return "Pager [currentPage=" + currentPage + ", isGetCount="
				+ isGetCount + ", pageSize=" + pageSize + ", startRow="
				+ startRow + ", totalPages=" + totalPages + ", totalRows="
				+ totalRows + "]";
	}
	
}
