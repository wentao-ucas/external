package cn.cncc.caos.external.client.cloud.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import java.util.Date;

@Schema(description = "云平台状态检查任务实例对象")
public class CloudTaskLogDto {
    @Schema(description = "任务实例ID", type = "string",defaultValue = "无", example = "TINST000001")
    private String taskinstid;
    @Schema(description = "任务ID", type = "string",defaultValue = "无", example = "T000001")
    private String taskid;
    @Schema(description = "记录状态,00:有效，01:无效", type = "string", defaultValue = "00", example = "01")
    private String status;

    @Schema(description = "任务执行结果", type = "string", defaultValue = "无", example = "{code:0,data:{app:{name:aaa,status:on}}}")
    private String result;

    @Schema(description = "结果解析", type = "string", defaultValue = "无", example = "{应用数=5,期望值:6}")
    private String parseresult;

    @Schema(description = "操作人", type = "string", defaultValue = "无", example = "dengjq")
    private String operator;

    @Schema(description = "任务启动时间", type = "Date", defaultValue = "无", example = "2024-11-20 11:11:11")
    private Date createtime;

    @Schema(description = "记录更新时间", type = "Date", defaultValue = "无", example = "2024-11-20 11:11:11")
    private Date updttime;
    @Schema(description = "任务实例状态,ST00: 成功，ST01: 失败，ST02: 执行中", type = "string", defaultValue = "无", example = "ST00")
    private String taskinststatus;
    @Schema(description = "任务结束时间", type = "Date", defaultValue = "无", example = "2024-11-20 11:11:11")
    private Date endtime;

    @Schema(description = "部门", type = "string", defaultValue = "无", example = "ywxtb")
    private String deptno;
    
    public String getTaskinstid() {
        return taskinstid;
    }

    
    public void setTaskinstid(String taskinstid) {
        this.taskinstid = taskinstid;
    }

    
    public String getTaskid() {
        return taskid;
    }

    
    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }

    
    public String getResult() {
        return result;
    }

    
    public void setResult(String result) {
        this.result = result;
    }

    
    public String getParseresult() {
        return parseresult;
    }

    
    public void setParseresult(String parseresult) {
        this.parseresult = parseresult;
    }

    
    public String getOperator() {
        return operator;
    }

    
    public void setOperator(String operator) {
        this.operator = operator;
    }

    
    public Date getCreatetime() {
        return createtime;
    }

    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    
    public Date getUpdttime() {
        return updttime;
    }

    
    public void setUpdttime(Date updttime) {
        this.updttime = updttime;
    }

    
    public String getTaskinststatus() {
        return taskinststatus;
    }

    
    public void setTaskinststatus(String taskinststatus) {
        this.taskinststatus = taskinststatus;
    }

    
    public Date getEndtime() {
        return endtime;
    }

    
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }
}