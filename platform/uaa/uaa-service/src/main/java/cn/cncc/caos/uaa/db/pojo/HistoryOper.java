package cn.cncc.caos.uaa.db.pojo;

import javax.annotation.Generated;
import java.util.Date;

public class HistoryOper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String userName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String operType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date operTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer depId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String realName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String operData;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(String id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUserName() {
        return userName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getOperType() {
        return operType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setOperType(String operType) {
        this.operType = operType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getOperTime() {
        return operTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getUserId() {
        return userId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getDepId() {
        return depId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getRealName() {
        return realName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getOperData() {
        return operData;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setOperData(String operData) {
        this.operData = operData;
    }
}