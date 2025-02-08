package cn.cncc.caos.platform.uaa.client.api.pojo;

import java.util.Date;

public class BaseSys {

    private Integer id;


    private String sysName;


    private String sysTitle;


    private String sysDesc;


    private Integer isBuiltIn;


    private String sysUrl;


    private String sysStatus;


    private String sysVersion;


    private String developer;


    private String imageName;


    private Integer interfaceNum;


    private String interfaceHelpUrl;


    private Integer isValid;


    private Date updateTime;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getSysName() {
        return sysName;
    }


    public void setSysName(String sysName) {
        this.sysName = sysName == null ? null : sysName.trim();
    }


    public String getSysTitle() {
        return sysTitle;
    }


    public void setSysTitle(String sysTitle) {
        this.sysTitle = sysTitle == null ? null : sysTitle.trim();
    }


    public String getSysDesc() {
        return sysDesc;
    }


    public void setSysDesc(String sysDesc) {
        this.sysDesc = sysDesc == null ? null : sysDesc.trim();
    }


    public Integer getIsBuiltIn() {
        return isBuiltIn;
    }


    public void setIsBuiltIn(Integer isBuiltIn) {
        this.isBuiltIn = isBuiltIn;
    }


    public String getSysUrl() {
        return sysUrl;
    }


    public void setSysUrl(String sysUrl) {
        this.sysUrl = sysUrl == null ? null : sysUrl.trim();
    }


    public String getSysStatus() {
        return sysStatus;
    }


    public void setSysStatus(String sysStatus) {
        this.sysStatus = sysStatus == null ? null : sysStatus.trim();
    }


    public String getSysVersion() {
        return sysVersion;
    }


    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion == null ? null : sysVersion.trim();
    }


    public String getDeveloper() {
        return developer;
    }


    public void setDeveloper(String developer) {
        this.developer = developer == null ? null : developer.trim();
    }


    public String getImageName() {
        return imageName;
    }


    public void setImageName(String imageName) {
        this.imageName = imageName == null ? null : imageName.trim();
    }


    public Integer getInterfaceNum() {
        return interfaceNum;
    }


    public void setInterfaceNum(Integer interfaceNum) {
        this.interfaceNum = interfaceNum;
    }


    public String getInterfaceHelpUrl() {
        return interfaceHelpUrl;
    }


    public void setInterfaceHelpUrl(String interfaceHelpUrl) {
        this.interfaceHelpUrl = interfaceHelpUrl == null ? null : interfaceHelpUrl.trim();
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