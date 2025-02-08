package cn.cncc.caos.external.client.cloud.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import java.util.Date;

/**
 * @className: CloudTaskLogVO
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/13 8:40
 */
@Schema(description = "云平台状态检查任务实例前端页面对象")
public class CloudTaskLogVO {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @Schema(description = "任务实例ID", type = "string",defaultValue = "无", example = "TINST000001")
    private String taskinstid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @Schema(description = "任务ID", type = "string",defaultValue = "无", example = "T000001")
    private String taskid;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @Schema(description = "任务描述", type = "string",defaultValue = "无", example = "应用组件数量检查任务")
    private String taskdesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @Schema(description = "任务定时配置", type = "string",defaultValue = "无", example = "* */5 * * *")
    private String croncfg;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @Schema(description = "结果解析", type = "string", defaultValue = "无", example = "{应用数:5,期望值:6}")
    private String parseresult;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @Schema(description = "任务实例状态,ST00: 成功，ST01: 失败，ST02: 执行中", type = "string", defaultValue = "无", example = "ST00")
    private String taskinststatus;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @Schema(description = "任务启动时间", type = "Date", defaultValue = "无", example = "2024-11-20 11:11:11")
    private Date createtime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @Schema(description = "任务结束时间", type = "Date", defaultValue = "无", example = "2024-11-20 11:11:11")
    private Date endtime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @Schema(description = "部门", type = "string", defaultValue = "无", example = "ywxtb")
    private String deptno;
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @Schema(description = "操作人", type = "string", defaultValue = "无", example = "dengjq")
    private String operator;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getTaskinstid() {
        return taskinstid;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setTaskinstid(String taskinstid) {
        this.taskinstid = taskinstid;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getTaskid() {
        return taskid;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getTaskdesc() {
        return taskdesc;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setTaskdesc(String taskdesc) {
        this.taskdesc = taskdesc;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getCroncfg() {
        return croncfg;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCroncfg(String croncfg) {
        this.croncfg = croncfg;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getParseresult() {
        return parseresult;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setParseresult(String parseresult) {
        this.parseresult = parseresult;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getTaskinststatus() {
        return taskinststatus;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setTaskinststatus(String taskinststatus) {
        this.taskinststatus = taskinststatus;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getCreatetime() {
        return createtime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getEndtime() {
        return endtime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getDeptno() {
        return deptno;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getOperator() {
        return operator;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setOperator(String operator) {
        this.operator = operator;
    }
}
