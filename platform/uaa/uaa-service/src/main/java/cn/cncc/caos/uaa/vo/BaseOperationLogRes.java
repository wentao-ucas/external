package cn.cncc.caos.uaa.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class BaseOperationLogRes {

    @Schema(description = "操作时间", type = "Date")
    private Date createTime;

    @Schema(description = "操作用户", type = "String")
    private String userRealName;

    @Schema(description = "用户部门", type = "String")
    private String depName;

    @Schema(description = "操作内容", type = "String")
    private String info;

    @Schema(description = "操作类型-码值", type = "Integer")
    private Integer type;

    @Schema(description = "操作类型-中文", type = "Integer")
    private String typeCn;
}
