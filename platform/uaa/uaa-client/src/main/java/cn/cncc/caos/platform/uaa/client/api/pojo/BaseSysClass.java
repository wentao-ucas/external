package cn.cncc.caos.platform.uaa.client.api.pojo;

import java.util.Date;

public class BaseSysClass {

    private Integer id;


    private String className;


    private String classDesc;


    private String sysAssemble;


    private Integer isValid;


    private Date updateTime;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getClassName() {
        return className;
    }


    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }


    public String getClassDesc() {
        return classDesc;
    }


    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc == null ? null : classDesc.trim();
    }


    public String getSysAssemble() {
        return sysAssemble;
    }


    public void setSysAssemble(String sysAssemble) {
        this.sysAssemble = sysAssemble == null ? null : sysAssemble.trim();
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