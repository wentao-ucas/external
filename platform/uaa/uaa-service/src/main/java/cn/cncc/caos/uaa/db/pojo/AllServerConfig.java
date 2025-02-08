package cn.cncc.caos.uaa.db.pojo;

import javax.annotation.Generated;
import java.util.Date;

public class AllServerConfig {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String module;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String function;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String configKey;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String configValue;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String configDesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String serverEnv;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String location;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date cTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date uTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(String id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getModule() {
        return module;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setModule(String module) {
        this.module = module;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getFunction() {
        return function;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setFunction(String function) {
        this.function = function;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getConfigKey() {
        return configKey;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getConfigValue() {
        return configValue;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getConfigDesc() {
        return configDesc;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setConfigDesc(String configDesc) {
        this.configDesc = configDesc;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getServerEnv() {
        return serverEnv;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setServerEnv(String serverEnv) {
        this.serverEnv = serverEnv;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getLocation() {
        return location;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setLocation(String location) {
        this.location = location;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getcTime() {
        return cTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getuTime() {
        return uTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getStatus() {
        return status;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setStatus(Byte status) {
        this.status = status;
    }
}