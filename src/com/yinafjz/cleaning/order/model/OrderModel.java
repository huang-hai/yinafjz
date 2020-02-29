package com.yinafjz.cleaning.order.model;

import java.util.ArrayList;
import java.util.List;

import com.yinafjz.cleaning.nanny.model.CompModel;
import com.yinafjz.cleaning.nanny.model.NannyModel;
import com.yinafjz.cleaning.user.model.AuntModel;

/**
 * @Title: OrderModel.java
 * @Package com.yinafjz.cleaning.user.model
 * @Description: 订单实体类
 * @author Huanghai
 * @date 2018-12-4 下午4:46:18
 * @version V1.0
 */
public class OrderModel {

	private int orderId; // 家政订单
	private int userId; // 用户主键
	private Integer couponId; //优惠券ID
	private String orderNumber; // 订单号
	private String serviceTime; // 预约服务时间
	private String beginTime; // 服务开始时间
	private String endTime; // 服务结束时间
	private String payTime; // 支付时间
	private double totalMoney; // 总额
	private double discounts; // 优惠
	private double actualPayment; // 实付款
	private String remark; // 备注
	private int state; // 订单状态(0关闭 1待支付 2进行中 3已完成4取消)
	private String cancelCause; // 取消服务原因
	private int itemId; // 服务商品id
	private String itemName; // 服务项目
	private boolean payStatus; // 支付状态
	private int attemperStatus; // 调度状态 0下单成功 1调度成功(已接单) 2上门服务 3完成
	private int auntId; // 服务阿姨
	private boolean payWay; // 支付方式 0线上支付 1货到付款
	private int payType; // 第三方支付类型 1支付宝 2微信
	private Integer orderType;//类型 0单次收费 1定金商品 2抢单
	private String payNum; // 第三方交易号
	private String customer; // 客户
	private String phone; // 手机号
	private String region; // 地址(省市区)
	private String address; // 详细地址
	private int origin; // 订单来源 1.app 2.微信客户端 3.浏览器
	private String completeTime; // 完成时间
	private String evaluateTime; // 评价时间
	private String createTime; // 创建时间
	private String updateTime; // 更新时间

	private CompModel compModel;//公司信息
	private Integer nannyId; //保姆id

	// 订单属性
	private List<OrderServiceModel> orderServices = new ArrayList<OrderServiceModel>();
	private AuntModel auntModel;

	/**
	 * 已关闭
	 */
	public static int ORDER_STATE_CLOSE = 0;
	/**
	 * 待支付
	 */
	public static int ORDER_STATE_UNPAID = 1;
	/**
	 * 进行中
	 */
	public static int ORDER_STATE_UNDERWAY = 2;
	/**
	 * 已完成
	 */
	public static int ORDER_STATE_COMPLETED = 3;
	/**
	 * 已取消
	 */
	public static int ORDER_STATE_CENCAL = 4;

	/**
	 * 下单成功
	 */
	public static int ATTEMPERSTATUS_SUCCESSFULLY = 0;
	/**
	 * 调度
	 */
	public static int ATTEMPERSTATUS_DISPATCH = 1;
	/**
	 * 上门服务
	 */
	public static int ATTEMPERSTATUS_VISITINGSERVICE = 2;
	/**
	 * 完成
	 */
	public static int ATTEMPERSTATUS_COMPLETE = 3;

	public Integer getNannyId() {
		return nannyId;
	}

	public void setNannyId(Integer nannyId) {
		this.nannyId = nannyId;
	}

	public CompModel getCompModel() {
		return compModel;
	}

	public void setCompModel(CompModel compModel) {
		this.compModel = compModel;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getAuntId() {
		return auntId;
	}

	public void setAuntId(int auntId) {
		this.auntId = auntId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAttemperStatus() {
		return attemperStatus;
	}

	public void setAttemperStatus(int attemperStatus) {
		this.attemperStatus = attemperStatus;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public double getDiscounts() {
		return discounts;
	}

	public void setDiscounts(double discounts) {
		this.discounts = discounts;
	}

	public double getActualPayment() {
		return actualPayment;
	}

	public void setActualPayment(double actualPayment) {
		this.actualPayment = actualPayment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getCancelCause() {
		return cancelCause;
	}

	public void setCancelCause(String cancelCause) {
		this.cancelCause = cancelCause;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public boolean isPayStatus() {
		return payStatus;
	}

	public void setPayStatus(boolean payStatus) {
		this.payStatus = payStatus;
	}

	public boolean isPayWay() {
		return payWay;
	}

	public void setPayWay(boolean payWay) {
		this.payWay = payWay;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getPayNum() {
		return payNum;
	}

	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getOrigin() {
		return origin;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getEvaluateTime() {
		return evaluateTime;
	}

	public void setEvaluateTime(String evaluateTime) {
		this.evaluateTime = evaluateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<OrderServiceModel> getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(List<OrderServiceModel> orderServices) {
		this.orderServices = orderServices;
	}

	public AuntModel getAuntModel() {
		return auntModel;
	}

	public void setAuntModel(AuntModel auntModel) {
		this.auntModel = auntModel;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
}
