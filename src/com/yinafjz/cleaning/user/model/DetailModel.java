/**   
 * @Title: DetailModel.java  
 * @Package com.yinafjz.cleaning.user.model  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author Huanghai 
 * @date 2018-12-4 下午3:10:53  
 * @version V1.0   
 */
package com.yinafjz.cleaning.user.model;

/**
 * @ClassName: DetailModel
 * @Description: 商品属性实体类(对应着ynf_homemaking.ynf_service_goods_attr表)
 * @author Huanghai
 * @date 2018-12-4 下午3:10:53
 * 
 */
public class DetailModel {

	private int detailId;// ` int(11) NOT NULL AUTO_INCREMENT COMMENT '服务详情',
	private int itemId;// ` int(11) DEFAULT NULL COMMENT '服务项id',
	private String type;// ` varchar(50) DEFAULT NULL COMMENT '服务属性名称',
	private double price;// ` double DEFAULT NULL COMMENT '服务价格',
	private String remark;// ` varchar(100) DEFAULT NULL COMMENT '备注',
	private String attrPic;// ` varchar(255) DEFAULT NULL COMMENT '服务项目图片',
//	private String attrType; //类别

	private ItemModel item;

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public String getAttrPic() {
		return attrPic;
	}

	public void setAttrPic(String attrPic) {
		this.attrPic = attrPic;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getType() {
		return type;
	}

	public ItemModel getItem() {
		return item;
	}

	public void setItem(ItemModel item) {
		this.item = item;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
