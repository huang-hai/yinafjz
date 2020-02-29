package com.yinafjz.cleaning.nanny.model;

import com.yinafjz.cleaning.user.model.EvaluationModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NannyModel {
    private Integer nannyId;

    private String number;

    private Integer compId;

    private String name;

    private String headImg;

    private String idCard;

    private String phone;

    private String type;

    private String nativePlace;

    private String nation;

    private Boolean marriage;

    private Integer age;

    private String city;

    private String education;

    private Integer experience;

    private Integer state;

    private Double salary;

    private String company;

    private Date createTime;

    private Date updateTime;

    private Boolean dataFlag;

    private Integer power;

    private Integer subscribeCount; //预约次数

    private Integer collectCount; //被收藏次数

    private Integer goodEvalCount;//好评次数

    private Integer onDutyCount;//总在岗次数

    private String servicePhone;//公司客服电话

    //标签
    private List<NannyLabelModel> labels = new ArrayList<NannyLabelModel>();

    private List<EvaluationModel> evals = new ArrayList<EvaluationModel>();

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public List<EvaluationModel> getEvals() {
        return evals;
    }

    public void setEvals(List<EvaluationModel> evals) {
        this.evals = evals;
    }

    public Integer getNannyId() {
        return nannyId;
    }

    public void setNannyId(Integer nannyId) {
        this.nannyId = nannyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public Boolean getMarriage() {
        return marriage;
    }

    public void setMarriage(Boolean marriage) {
        this.marriage = marriage;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
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

    public Boolean getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(Boolean dataFlag) {
        this.dataFlag = dataFlag;
    }

    public List<NannyLabelModel> getLabels() {
        return labels;
    }

    public void setLabels(List<NannyLabelModel> labels) {
        this.labels = labels;
    }

    public Integer getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(Integer subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getGoodEvalCount() {
        return goodEvalCount;
    }

    public void setGoodEvalCount(Integer goodEvalCount) {
        this.goodEvalCount = goodEvalCount;
    }

    public Integer getOnDutyCount() {
        return onDutyCount;
    }

    public void setOnDutyCount(Integer onDutyCount) {
        this.onDutyCount = onDutyCount;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}