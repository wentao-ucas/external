package cn.cncc.caos.uaa.db.pojo;

import javax.annotation.Generated;

public class BaseRoleRelRole {

    private Integer id;


    private Integer roleIdKey;


    private Integer roleIdValue;


    private String roleRelUsage;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getRoleIdKey() {
        return roleIdKey;
    }


    public void setRoleIdKey(Integer roleIdKey) {
        this.roleIdKey = roleIdKey;
    }


    public Integer getRoleIdValue() {
        return roleIdValue;
    }


    public void setRoleIdValue(Integer roleIdValue) {
        this.roleIdValue = roleIdValue;
    }


    public String getRoleRelUsage() {
        return roleRelUsage;
    }


    public void setRoleRelUsage(String roleRelUsage) {
        this.roleRelUsage = roleRelUsage == null ? null : roleRelUsage.trim();
    }
}