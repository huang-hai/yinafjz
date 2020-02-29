package com.yinafjz.cleaning.user.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CategoryModel
 * @Description: 分类实体类(对应着ynf_homemaking.ynf_service_category表)
 * @author Huanghai
 * @date 2018-12-3 下午5:57:31
 * 
 */
public class CategoryModel {

	private int cateId;// ` int(11) NOT NULL AUTO_INCREMENT COMMENT '服务分类',
	private String cateName;// ` varchar(30) DEFAULT NULL COMMENT '分类名称',
	private String cateImage;// ` varchar(120) DEFAULT NULL COMMENT '分类封面',
	private String cateIcon; // 分类图标
	private boolean isShow; // tinyint(4) DEFAULT NULL COMMENT '首页是否显示',

	private List<ItemModel> items = new ArrayList<ItemModel>();

	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getCateImage() {
		return cateImage;
	}

	public void setCateImage(String cateImage) {
		this.cateImage = cateImage;
	}

	public String getCateIcon() {
		return cateIcon;
	}

	public void setCateIcon(String cateIcon) {
		this.cateIcon = cateIcon;
	}

	public List<ItemModel> getItems() {
		return items;
	}

	public void setItems(List<ItemModel> items) {
		this.items = items;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

}
