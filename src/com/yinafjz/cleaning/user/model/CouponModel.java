package com.yinafjz.cleaning.user.model;

public class CouponModel {

    private Integer couponId;//` int(11) NOT NULL AUTO_INCREMENT COMMENT '优惠券',
    private String couponName;//` varchar(100) DEFAULT NULL COMMENT '名称',
    private Integer goodsId;//` int(11) DEFAULT NULL COMMENT '商品ID',
    private Integer userId;//` int(11) DEFAULT NULL COMMENT '用户ID',
    private Double money;//` double DEFAULT NULL COMMENT '金额',
    private Double pull;//` double DEFAULT NULL COMMENT '满多少可以使用',
    private Integer state;//` int(1) DEFAULT '1' COMMENT '状态(0过期 1有效 2已使用)',
    private String beginTime;//` datetime DEFAULT NULL COMMENT '有效期开始时间',
    private String endTime;//` datetime DEFAULT NULL COMMENT '有效期结束时间',
    private String createTime;//` datetime DEFAULT NULL COMMENT '创建时间',

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getPull() {
        return pull;
    }

    public void setPull(Double pull) {
        this.pull = pull;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
