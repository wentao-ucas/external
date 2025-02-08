package cn.cncc.caos.uaa.db.pojo;

import java.util.Date;
import javax.annotation.Generated;

public class BaseUserExtend {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte dutyManager;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte primaryDutyManager;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte approverMobile;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getUserId() {
        return userId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getDutyManager() {
        return dutyManager;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDutyManager(Byte dutyManager) {
        this.dutyManager = dutyManager;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getPrimaryDutyManager() {
        return primaryDutyManager;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPrimaryDutyManager(Byte primaryDutyManager) {
        this.primaryDutyManager = primaryDutyManager;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getApproverMobile() {
        return approverMobile;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setApproverMobile(Byte approverMobile) {
        this.approverMobile = approverMobile;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getStatus() {
        return status;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setStatus(Byte status) {
        this.status = status;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getCreateTime() {
        return createTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getUpdateTime() {
        return updateTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}