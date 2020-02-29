package com.yinafjz.cleaning.nanny.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class NannySubscribeModel {
    private Integer subscribeId;

    @NotNull(message = "用户ID不允许为空")
    @Min(value = 1,message = "用户ID不能小于1")
    private Integer userId;

    @NotNull(message = "保姆ID不允许为空")
    @Min(value = 1,message = "保姆ID不能小于1")
    private Integer nannyId;

    @NotNull(message = "预约时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    @NotNull(message = "面试方式不能为空")
    @Min(value = 0,message = "面试方式不能小于0")
    @Max(value = 3,message = "面试方式不能大于3")
    private Integer interviewType;

    private Integer state;

    private String remark;

    private Date createTime;

    public Integer getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(Integer subscribeId) {
        this.subscribeId = subscribeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNannyId() {
        return nannyId;
    }

    public void setNannyId(Integer nannyId) {
        this.nannyId = nannyId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(Integer interviewType) {
        this.interviewType = interviewType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}