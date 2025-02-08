package cn.cncc.caos.uaa.db.pojo;

import javax.annotation.Generated;
import java.util.Date;

public class BaseRoleAuthPermission {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String roleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String permissionId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date updateTime;

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
    public String getRoleId() {
        return roleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getPermissionId() {
        return permissionId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
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

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getStatus() {
        return status;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setStatus(Byte status) {
        this.status = status;
    }
}