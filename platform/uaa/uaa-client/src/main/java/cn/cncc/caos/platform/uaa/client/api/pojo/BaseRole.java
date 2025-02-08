package cn.cncc.caos.platform.uaa.client.api.pojo;

import java.util.Date;

public class BaseRole {

    private Integer id;


    private String roleName;


    private String roleDesc;


    private Integer sysId;


    private Integer isValid;


    private Date updateTime;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getRoleName() {
        return roleName;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }


    public String getRoleDesc() {
        return roleDesc;
    }


    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }


    public Integer getSysId() {
        return sysId;
    }


    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }


    public Integer getIsValid() {
        return isValid;
    }


    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }


    public Date getUpdateTime() {
        return updateTime;
    }


    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}