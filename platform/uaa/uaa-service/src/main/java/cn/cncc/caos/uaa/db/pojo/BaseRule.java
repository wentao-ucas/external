package cn.cncc.caos.uaa.db.pojo;

import javax.annotation.Generated;
import java.util.Date;

public class BaseRule {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String name;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String title;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String formula;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String parentId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer seq;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date updateTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(String id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getName() {
        return name;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setName(String name) {
        this.name = name;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getTitle() {
        return title;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getFormula() {
        return formula;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getParentId() {
        return parentId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getSeq() {
        return seq;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getStatus() {
        return status;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setStatus(Byte status) {
        this.status = status;
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
}