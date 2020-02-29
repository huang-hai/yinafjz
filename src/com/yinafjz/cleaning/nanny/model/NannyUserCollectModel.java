package com.yinafjz.cleaning.nanny.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class NannyUserCollectModel {
    private Integer collectId;

    @NotNull(message = "用户ID不允许为空")
    @Min(value = 1,message = "用户ID不能小于1")
    private Integer userId;

    @NotNull(message = "保姆ID不允许为空")
    @Min(value = 1,message = "保姆ID不能小于1")
    private Integer nanny;

    private Date createTime;

    private Date updateTime;

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNanny() {
        return nanny;
    }

    public void setNanny(Integer nanny) {
        this.nanny = nanny;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}