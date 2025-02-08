package cn.cncc.caos.uaa.db.pojo;

import java.util.Date;
import javax.annotation.Generated;

public class BaseCompany {

    private Integer id;


    private String companyName;


    private String contactInfo;


    private String chargeList;


    private Integer isValid;


    private Date updateTime;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getCompanyName() {
        return companyName;
    }


    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }


    public String getContactInfo() {
        return contactInfo;
    }


    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo == null ? null : contactInfo.trim();
    }


    public String getChargeList() {
        return chargeList;
    }


    public void setChargeList(String chargeList) {
        this.chargeList = chargeList == null ? null : chargeList.trim();
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