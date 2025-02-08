package cn.cncc.caos.platform.uaa.client.api.pojo;

import java.util.Date;

public class BaseUserRelRole {

    private Integer id;


    private Integer roleId;


    private Integer userId;


    private Integer isValid;


    private Date updateTime;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getRoleId() {
        return roleId;
    }


    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
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