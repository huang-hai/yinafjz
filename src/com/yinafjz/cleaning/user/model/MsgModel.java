package com.yinafjz.cleaning.user.model;

/**
 * @Title: MsgModel.java
 * @Package com.yinafjz.cleaning.user.model
 * @Description: 消息实体类
 * @author Huanghai
 * @date 2018-12-21 下午2:19:33
 * @version V1.0
 */
public class MsgModel {

	private int msgId;// ` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息id',
	private int userId;
	private int msgType; // 消息类型 0交易提醒 1平台通知
	private String msgImg; // 活动图片
	private String msgTitle;// ` varchar(255) DEFAULT NULL COMMENT '消息标题',
	private String msgContent;// ` varchar(255) DEFAULT NULL COMMENT '消息内容',
	private String createTime;// ` datetime DEFAULT NULL COMMENT '创建日期',
	private boolean isReader;// ` tinyint(4) DEFAULT '0' COMMENT '是否已读 0未读 1已读',

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public boolean isReader() {
		return isReader;
	}

	public void setReader(boolean isReader) {
		this.isReader = isReader;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getMsgImg() {
		return msgImg;
	}

	public void setMsgImg(String msgImg) {
		this.msgImg = msgImg;
	}

}
