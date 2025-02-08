package cn.cncc.caos.uaa.db.pojo;

import javax.annotation.Generated;
import java.util.Date;

public class BasePost {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String postCode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String postName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer postSort;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String createUser;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String updateUser;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String remark;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(String id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getPostCode() {
        return postCode;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getPostName() {
        return postName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getPostSort() {
        return postSort;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPostSort(Integer postSort) {
        this.postSort = postSort;
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
    public String getCreateUser() {
        return createUser;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
    public String getUpdateUser() {
        return updateUser;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getUpdateTime() {
        return updateTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getRemark() {
        return remark;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRemark(String remark) {
        this.remark = remark;
    }
}