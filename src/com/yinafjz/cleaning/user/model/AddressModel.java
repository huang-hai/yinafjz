package com.yinafjz.cleaning.user.model;

/**
 * @ClassName: AddressModel
 * @Description: TODO(服务地址实体类)
 * @author Huanghai
 * @date 2018-12-3 下午5:57:54
 * 
 */
public class AddressModel {

	private int auId;// ` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	private int userId;// ` int(11) NOT NULL COMMENT '用户主键',
	private int communityId; // 小区id
	private String receiver;// ` varchar(30) DEFAULT NULL COMMENT '收件人',
	private String phone;// ` varchar(20) DEFAULT NULL COMMENT '手机号',
	private String region;// ` varchar(50) DEFAULT NULL COMMENT '区域 格式 省市区',
	private String address;// ` varchar(255) DEFAULT NULL COMMENT '详细地址',
	private String zipcode;// ` varchar(10) DEFAULT NULL COMMENT '邮编',
	private String communityName;// 社区名称
	private boolean defcode;// ` tinyint(4) DEFAULT '0' COMMENT '默认地址 0否
							// 1是(默认地址只有一个)',
	private String createTime;// ` datetime DEFAULT NULL COMMENT '创建时间',
	private String updateTime;// ` timestamp NULL DEFAULT NULL ON UPDATE
								// CURRENT_TIMESTAMP COMMENT '最后修改时间',

	public int getAuId() {
		return auId;
	}

	public void setAuId(int auId) {
		this.auId = auId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
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

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public boolean isDefcode() {
		return defcode;
	}

	public void setDefcode(boolean defcode) {
		this.defcode = defcode;
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

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

}
