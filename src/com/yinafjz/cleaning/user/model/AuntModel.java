package com.yinafjz.cleaning.user.model;

/**
 * @ClassName: AuntModel
 * @Description: 阿姨实体类
 * @author Huanghai
 * @date 2018-12-6 下午2:13:31
 * 
 */
public class AuntModel {

	private int auntId;// ` int(11) NOT NULL AUTO_INCREMENT COMMENT '阿姨id',
	private String userName;// ` varchar(30) DEFAULT NULL COMMENT '用户名(电话号码)',
	private String trueName;// ` varchar(20) DEFAULT NULL COMMENT '真实姓名',
	private String auntImage;// ` varchar(255) DEFAULT NULL COMMENT '头像',
	private boolean authType;// ` tinyint(3) DEFAULT NULL COMMENT '身份认证状态 0未认证
								// 1已认证',
	private boolean auntType;// ` tinyint(3) DEFAULT NULL COMMENT '账户是否存在异常 0异常
								// 1正常',

	public int getAuntId() {
		return auntId;
	}

	public void setAuntId(int auntId) {
		this.auntId = auntId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getAuntImage() {
		return auntImage;
	}

	public void setAuntImage(String auntImage) {
		this.auntImage = auntImage;
	}

	public boolean isAuthType() {
		return authType;
	}

	public void setAuthType(boolean authType) {
		this.authType = authType;
	}

	public boolean isAuntType() {
		return auntType;
	}

	public void setAuntType(boolean auntType) {
		this.auntType = auntType;
	}

}
