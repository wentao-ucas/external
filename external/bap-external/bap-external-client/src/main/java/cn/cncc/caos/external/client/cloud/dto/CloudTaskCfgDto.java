package cn.cncc.caos.external.client.cloud.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import java.util.Date;

@Schema(description = "云平台状态检查任务对象")
public class CloudTaskCfgDto {
    @Schema(description = "任务ID", type = "string",defaultValue = "无", example = "T000001")
    private String taskid;
    @Schema(description = "任务描述", type = "string",defaultValue = "无", example = "应用组件数量检查任务")
    private String taskdesc;
    @Schema(description = "任务对应的URL", type = "string",defaultValue = "无", example = "http://hcs.console.cncc.cn/cce?tenant=xxx")
    private String url;
    @Schema(description = "http请求类型", type = "string",defaultValue = "GET", example = "POST")
    private String reqtype;
    @Schema(description = "任务操作域类型，1:运维面，0:运营面", type = "string",defaultValue = "无", example = "0")
    private String planetype;
    @Schema(description = "任务参数", type = "string",defaultValue = "无", example = "project=ywxtb01")
    private String param;
    @Schema(description = "API对应授权认证ID", type = "string",defaultValue = "无", example = "huawei_beijing_opm_0")
    private String authid;
    @Schema(description = "任务定时配置", type = "string",defaultValue = "无", example = "* */5 * * *")
    private String croncfg;
    @Schema(description = "任务结果解析规则,fieldExpr字段为jsonpath格式（如$.data.book[*].author）或者正则表达式,exprType字段:0(直接取值)、1(统计个数)、2(字段求和)、3(正则表达式)", type = "string",defaultValue = "无", example = "{[{fieldName:xx,fieldExpr:yy,exprType:0,expect:3},{...}]}")
    private String parserule;
    @Schema(description = "记录状态,00:有效，01:无效", type = "string", defaultValue = "00", example = "01")
    private String status;
    @Schema(description = "操作人", type = "string", defaultValue = "无", example = "dengjq")
    private String operator;
    @Schema(description = "部门", type = "string", defaultValue = "无", example = "ywxtb")
    private String deptno;
    @Schema(description = "任务创建时间", type = "Date", defaultValue = "无", example = "2024-11-11 11:11:11")
    private Date createtime;
    @Schema(description = "备注", type = "string", defaultValue = "无", example = "检查华为云，值班使用")
    private String remark;
    @Schema(description = "记录更新时间", type = "Date", defaultValue = "无", example = "2024-11-20 11:11:11")
    private Date updttime;
    @Schema(description = "任务状态,ST00: 启用，ST02: 停止，ST03: 运行中，ST04: 失效", type = "string", defaultValue = "ST00", example = "ST03")
    private String taskstatus;

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTaskdesc() {
        return taskdesc;
    }

    public void setTaskdesc(String taskdesc) {
        this.taskdesc = taskdesc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReqtype() {
        return reqtype;
    }

    public void setReqtype(String reqtype) {
        this.reqtype = reqtype;
    }

    
    public String getPlanetype() {
        return planetype;
    }

    
    public void setPlanetype(String planetype) {
        this.planetype = planetype;
    }

    
    public String getParam() {
        return param;
    }

    
    public void setParam(String param) {
        this.param = param;
    }

    
    public String getAuthid() {
        return authid;
    }

    
    public void setAuthid(String authid) {
        this.authid = authid;
    }

    
    public String getCroncfg() {
        return croncfg;
    }

    
    public void setCroncfg(String croncfg) {
        this.croncfg = croncfg;
    }

    
    public String getParserule() {
        return parserule;
    }

    
    public void setParserule(String parserule) {
        this.parserule = parserule;
    }

    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }

    
    public String getOperator() {
        return operator;
    }

    
    public void setOperator(String operator) {
        this.operator = operator;
    }

    
    public String getDeptno() {
        return deptno;
    }

    
    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    
    public Date getCreatetime() {
        return createtime;
    }

    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    
    public Date getUpdttime() {
        return updttime;
    }

    
    public void setUpdttime(Date updttime) {
        this.updttime = updttime;
    }

    
    public String getTaskstatus() {
        return taskstatus;
    }

    
    public void setTaskstatus(String taskstatus) {
        this.taskstatus = taskstatus;
    }
}