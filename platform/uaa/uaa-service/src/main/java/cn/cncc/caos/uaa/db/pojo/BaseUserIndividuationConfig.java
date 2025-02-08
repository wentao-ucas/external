package cn.cncc.caos.uaa.db.pojo;

import java.util.Date;
import javax.annotation.Generated;

public class BaseUserIndividuationConfig {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String moduleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String moduleName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String systemId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte mailStatus;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte smsStatus;

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
    public String getModuleId() {
        return moduleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getModuleName() {
        return moduleName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getSystemId() {
        return systemId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getMailStatus() {
        return mailStatus;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setMailStatus(Byte mailStatus) {
        this.mailStatus = mailStatus;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getSmsStatus() {
        return smsStatus;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setSmsStatus(Byte smsStatus) {
        this.smsStatus = smsStatus;
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