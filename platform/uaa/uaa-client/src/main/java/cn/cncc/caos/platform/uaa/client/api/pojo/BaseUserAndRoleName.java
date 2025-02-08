package cn.cncc.caos.platform.uaa.client.api.pojo;

import java.io.Serializable;
import java.util.Date;

public class BaseUserAndRoleName implements Serializable {

    private Integer id;


    private String userName;


    private String realName;


    private String password;


    private String phone;


    private String email;


    private Date lastLoginTime;


    private String imageUrl;


    private Integer isOnline;


    private Integer isAdmin;


    private Date createTime;


    private Integer depId;


    private String locationName;


    private Integer isValid;


    private Date updateTime;


    private Integer isPublicUser;


    private String companyName;


    private String roleName;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }


    public String getRealName() {
        return realName;
    }


    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }


    public Date getLastLoginTime() {
        return lastLoginTime;
    }


    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }


    public Integer getIsOnline() {
        return isOnline;
    }


    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }


    public Integer getIsAdmin() {
        return isAdmin;
    }


    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getDepId() {
        return depId;
    }


    public void setDepId(Integer depId) {
        this.depId = depId;
    }


    public String getLocationName() {
        return locationName;
    }


    public void setLocationName(String locationName) {
        this.locationName = locationName == null ? null : locationName.trim();
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


    public Integer getIsPublicUser() {
        return isPublicUser;
    }


    public void setIsPublicUser(Integer isPublicUser) {
        this.isPublicUser = isPublicUser;
    }


    public String getCompanyName() {
        return companyName;
    }


    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getRoleName() {
        return roleName;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }


}