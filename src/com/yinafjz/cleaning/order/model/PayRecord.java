package com.yinafjz.cleaning.order.model;

import java.math.BigDecimal;

public class PayRecord {

	private Integer id;
    private Integer payType;		//支付类型 1=微信，2=支付宝
    private String transactionNo;	//交易号(微信的 transaction_id / 支付宝的 trade_no)
    private String outTradeNo;		//商户订单编号
    private Integer status;			//状态: 0=未支付 ， 1 = 支付成功
    private String payerId;			//支付用户id （微信的openid，支付宝的buyer_id）
    private Integer orderId;		//订单id
    private String orderNum;		//订单号
    private BigDecimal payAmount;	//支付金额
    private String createTime;		//创建时间
    private String payTime;			//支付时间
    private String dataPackage;		//数据包
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPayerId() {
		return payerId;
	}
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getDataPackage() {
		return dataPackage;
	}
	public void setDataPackage(String dataPackage) {
		this.dataPackage = dataPackage;
	}
}