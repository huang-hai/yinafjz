package com.yinafjz.cleaning.user.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ItemModel.java
 * @Package com.yinafjz.cleaning.user.model
 * @Description: 商品实体类(对应着ynf_homemaking.ynf_service_goods表)
 * @author Huanghai
 * @date 2018-12-4 上午9:06:49
 * @version V1.0
 */
public class ItemModel {

	private int itemId;// ` int(11) NOT NULL AUTO_INCREMENT COMMENT '服务项',
	private int cateId;// ` int(11) DEFAULT NULL COMMENT '分类id',
	private Integer compId; //公司ID
	private String itemName;// ` varchar(50) DEFAULT NULL COMMENT '服务项名称',
	private String pic;// ` varchar(100) DEFAULT NULL COMMENT '服务项图片',
	private String intro;// ` varchar(50) DEFAULT NULL COMMENT '介绍',
	private String shortIntro;// ` varchar(50) DEFAULT NULL COMMENT '短介绍',
	private double originalPrice;// ` double DEFAULT NULL COMMENT '原价',
	private double currentPrice;// ` double DEFAULT NULL COMMENT '现价',
	private String scope;// ` varchar(250) DEFAULT NULL COMMENT '服务范围',
	private String process;// ` varchar(250) DEFAULT NULL COMMENT '服务流程',

	private int goodsSales;// ` int(10) DEFAULT NULL COMMENT '销售数量',
	private Integer goodsType;// ` tinyint(4) DEFAULT '1' COMMENT '商品类型(0单次收费 1定金商品)',

	private int indexType;// ` tinyint(4) DEFAULT NULL COMMENT '1首页热门推荐 2精选套餐',
	private boolean isType; // '是否是服务时长类型(0不是 1是)',
	private String goodsDetails;//` varchar(250) DEFAULT NULL COMMENT '商品详情(图片)',
	private Boolean goodsState;//` tinyint(4) DEFAULT '1' COMMENT '商品状态(0下架 1上架)',
	private Boolean isHot;//` tinyint(4) DEFAULT '0' COMMENT '是否热销(0不是 1是)',
	private Boolean isCxiao;//` tinyint(4) DEFAULT '0' COMMENT '是否畅销(0不是 1是)'
	/**
	 * 热门推荐
	 */
	public static int INDEX_TYPE_RECOMMEND = 1;
	/**
	 * 精选套餐
	 */
	public static int INDEX_TYPE_COMBO = 2;

	private List<DetailModel> details = new ArrayList<DetailModel>();
	private CategoryModel category;

	public Integer getCompId() {
		return compId;
	}

	public void setCompId(Integer compId) {
		this.compId = compId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getGoodsSales() {
		return goodsSales;
	}

	public void setGoodsSales(int goodsSales) {
		this.goodsSales = goodsSales;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public CategoryModel getCategory() {
		return category;
	}

	public void setCategory(CategoryModel category) {
		this.category = category;
	}

	public List<DetailModel> getDetails() {
		return details;
	}

	public void setDetails(List<DetailModel> details) {
		this.details = details;
	}

	public String getShortIntro() {
		return shortIntro;
	}

	public void setShortIntro(String shortIntro) {
		this.shortIntro = shortIntro;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public int getIndexType() {
		return indexType;
	}

	public void setIndexType(int indexType) {
		this.indexType = indexType;
	}

	public boolean isType() {
		return isType;
	}

	public void setType(boolean isType) {
		this.isType = isType;
	}

	public String getGoodsDetails() {
		return goodsDetails;
	}

	public void setGoodsDetails(String goodsDetails) {
		this.goodsDetails = goodsDetails;
	}

	public Boolean getGoodsState() {
		return goodsState;
	}

	public void setGoodsState(Boolean goodsState) {
		this.goodsState = goodsState;
	}

	public Boolean getHot() {
		return isHot;
	}

	public void setHot(Boolean hot) {
		isHot = hot;
	}

	public Boolean getCxiao() {
		return isCxiao;
	}

	public void setCxiao(Boolean cxiao) {
		isCxiao = cxiao;
	}
}
