package com.yinafjz.cleaning.order.model;

import com.yinafjz.cleaning.user.model.DetailModel;

/**
 * @Title: OrderServiceModel.java
 * @Package com.yinafjz.cleaning.user.model
 * @Description: 订单的服务实体类(ynf_homemaking.ynf_order_attr表)
 * @author Huanghai
 * @date 2018-12-5 上午9:40:01
 * @version V1.0
 */
public class OrderServiceModel {

	private int osId;// ` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单服务',
	private int orderId;// ` int(11) DEFAULT NULL COMMENT '订单id',
	private int dtId;// ` int(11) DEFAULT NULL COMMENT '服务详情id',
	private int num;// ` int(11) DEFAULT NULL COMMENT '服务数量',

	private String name; // varchar(50) DEFAULT NULL COMMENT '服务属性名称',
	private double price; // 服务属性价格
	private String pic; // 服务属性图片

	private DetailModel detailModel;

	public int getOsId() {
		return osId;
	}

	public void setOsId(int osId) {
		this.osId = osId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getDtId() {
		return dtId;
	}

	public void setDtId(int dtId) {
		this.dtId = dtId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public DetailModel getDetailModel() {
		return detailModel;
	}

	public void setDetailModel(DetailModel detailModel) {
		this.detailModel = detailModel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

}
