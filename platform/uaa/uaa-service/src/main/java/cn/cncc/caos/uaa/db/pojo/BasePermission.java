package cn.cncc.caos.uaa.db.pojo;

import java.util.Date;
import javax.annotation.Generated;

public class BasePermission {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String name;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String title;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String path;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String parentName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer seq;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String env;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte notice;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(String id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getName() {
        return name;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setName(String name) {
        this.name = name;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getTitle() {
        return title;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getPath() {
        return path;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPath(String path) {
        this.path = path;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getParentName() {
        return parentName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getSeq() {
        return seq;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setSeq(Integer seq) {
        this.seq = seq;
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
    public String getEnv() {
        return env;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setEnv(String env) {
        this.env = env;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getNotice() {
        return notice;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setNotice(Byte notice) {
        this.notice = notice;
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