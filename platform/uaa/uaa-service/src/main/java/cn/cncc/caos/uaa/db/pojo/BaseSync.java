package cn.cncc.caos.uaa.db.pojo;

import javax.annotation.Generated;
import java.util.Date;

public class BaseSync {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date syncTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte syncType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte isValid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(String id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getSyncTime() {
        return syncTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getSyncType() {
        return syncType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setSyncType(Byte syncType) {
        this.syncType = syncType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getIsValid() {
        return isValid;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }
}