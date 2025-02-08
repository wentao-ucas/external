package cn.cncc.caos.data.client.pubparam.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * @className: PubParamDto
 * @Description: 公共参数DTO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/9 10:16
 */
@Schema(description = "公共配置参数对象")
public class PubParamDto {
    @Schema(description = "参数ID", type = "string", example = "huawei_beijing_opm_0")
    private String parmid;

    @Schema(description = "参数名称", type = "string", defaultValue = "无", example = "华为云北京运维面账户信息")
    private String parmname;

    @Schema(description = "参数值", type = "string", defaultValue = "无", example = "{url=xxx,grantType=yyy,userName=zzz,value=ttt}")
    private String parmval;

    @Schema(description = "是否加密", type = "string", defaultValue = "UENC", example = "ENC")
    private String encryptflag;

    @Schema(description = "参数描述", type = "string", defaultValue = "无", example = "华为云北京站点运维面登录账号信息")
    private String parmdesc;

    @Schema(description = "操作人", type = "string", defaultValue = "无", example = "dengjq")
    private String operator;

    @Schema(description = "记录更新时间", type = "Date", defaultValue = "无", example = "2024-11-20 11:11:11")
    private Date updttime;

    //参数类型，00:普通参数,01:华为云认证参数
    @Schema(description = "参数类型,00:普通参数，01:华为云认证参数", type = "string", defaultValue = "00", example = "01")
    private String type;

    @Schema(description = "记录创建时间", type = "Date", defaultValue = "无", example = "2024-11-20 11:11:11")
    private Date createtime;
    @Schema(description = "记录状态,00:有效，01:无效", type = "string", defaultValue = "00", example = "01")
    private String status;

    public String getParmid() {
        return parmid;
    }

    public void setParmid(String parmid) {
        this.parmid = parmid;
    }

    public String getParmname() {
        return parmname;
    }

    public void setParmname(String parmname) {
        this.parmname = parmname;
    }

    public String getParmval() {
        return parmval;
    }

    public void setParmval(String parmval) {
        this.parmval = parmval;
    }

    public String getEncryptflag() {
        return encryptflag;
    }

    public void setEncryptflag(String encryptflag) {
        this.encryptflag = encryptflag;
    }

    public String getParmdesc() {
        return parmdesc;
    }

    public void setParmdesc(String parmdesc) {
        this.parmdesc = parmdesc;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getUpdttime() {
        return updttime;
    }

    public void setUpdttime(Date updttime) {
        this.updttime = updttime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PubParamDto{" +
                "parmid='" + parmid + '\'' +
                ", parmname='" + parmname + '\'' +
                ", parmval='" + parmval + '\'' +
                ", encryptflag='" + encryptflag + '\'' +
                ", parmdesc='" + parmdesc + '\'' +
                ", operator='" + operator + '\'' +
                ", updttime=" + updttime +
                ", type='" + type + '\'' +
                ", createtime=" + createtime +
                ", status='" + status + '\'' +
                '}';
    }
}
