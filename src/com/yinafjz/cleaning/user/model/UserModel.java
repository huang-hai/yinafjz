package com.yinafjz.cleaning.user.model;

public class UserModel {

	private Integer userId;
	private String userName;
	private String passWord;
	private String image;			//头像
	private String realName;		//昵称
	private String phone;			//电话
	private String mail;			//邮箱
	private Integer sex;			//性别 0未选择  1男 2女
	private String birthday;		//生日
	private String blood;			//血型
	private String occupation;		//工作职业
	private String householdRegister;//户籍地
	private String domicile;		//所在地
	private String createTime;		//创建时间
	private String updateTime;		//更新时间
	private Integer userType;			//会员类型 0普通会员  1vip会员
	private Integer userState;			//用户状态（0 -禁用，1 - 启用）
	private Integer dataFlag;			//0:删除 1:有效
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getBlood() {
		return blood;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getHouseholdRegister() {
		return householdRegister;
	}
	public void setHouseholdRegister(String householdRegister) {
		this.householdRegister = householdRegister;
	}
	public String getDomicile() {
		return domicile;
	}
	public void setDomicile(String domicile) {
		this.domicile = domicile;
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
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getUserState() {
		return userState;
	}
	public void setUserState(Integer userState) {
		this.userState = userState;
	}
	public Integer getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(Integer dataFlag) {
		this.dataFlag = dataFlag;
	}
	
}
