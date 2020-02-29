package com.yinafjz.cleaning.nanny.model;

public class CompModel {

    private Integer compId;//` int(11) NOT NULL AUTO_INCREMENT COMMENT '第三方公司',
    private String compName;//` varchar(255) DEFAULT NULL COMMENT '公司名称',
    private String servicePhone;//` varchar(20) DEFAULT NULL COMMENT '公司客服电话',

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }
}
