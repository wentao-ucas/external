package cn.cncc.caos.platform.uaa.client.api.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;

import java.util.Date;

@ToString
public class BaseDep {

    private Integer id;


    private String depName;


    private String depDesc;


    private Integer parentId;


    private Integer level;


    private Integer isValid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String depCode;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getDepName() {
        return depName;
    }


    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }


    public String getDepDesc() {
        return depDesc;
    }


    public void setDepDesc(String depDesc) {
        this.depDesc = depDesc == null ? null : depDesc.trim();
    }


    public Integer getParentId() {
        return parentId;
    }


    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


//    public Integer getLevel() {
//        return level;
//    }
//
//
//    public void setLevel(Integer level) {
//        this.level = level;
//    }


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

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }


}