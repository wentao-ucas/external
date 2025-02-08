package cn.cncc.caos.uaa.db.pojo;

import javax.annotation.Generated;

public class BaseDict {

    private Integer id;


    private String dictType;


    private String dictCode;


    private String dictValue;


    private String dictDesc;


    private Integer dictSeq;


    private Integer parentId;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getDictType() {
        return dictType;
    }


    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }


    public String getDictCode() {
        return dictCode;
    }


    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }


    public String getDictValue() {
        return dictValue;
    }


    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }


    public String getDictDesc() {
        return dictDesc;
    }


    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc == null ? null : dictDesc.trim();
    }


    public Integer getDictSeq() {
        return dictSeq;
    }


    public void setDictSeq(Integer dictSeq) {
        this.dictSeq = dictSeq;
    }


    public Integer getParentId() {
        return parentId;
    }


    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}