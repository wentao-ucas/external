package cn.cncc.caos.platform.uaa.client.api.pojo;

import javax.annotation.Generated;
import java.util.Date;

public class BaseRoleAuth {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String roleName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String roleDesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer sysId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte isValid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String roleKey;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(String id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getRoleName() {
        return roleName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getRoleDesc() {
        return roleDesc;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getSysId() {
        return sysId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getIsValid() {
        return isValid;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
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
    public String getRoleKey() {
        return roleKey;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }
}