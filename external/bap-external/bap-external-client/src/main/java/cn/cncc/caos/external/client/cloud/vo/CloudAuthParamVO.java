package cn.cncc.caos.external.client.cloud.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @className: CloudAuthParamVO
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/13 16:05
 */
@Getter
@Setter
@Schema(description = "云平台认证授权参数")
public class CloudAuthParamVO {
    @Schema(description = "云平台认证授权ID", type = "string",defaultValue = "无", example = "huawei_beijing_opm_0")
    private String authId;
    @Schema(description = "云平台认证授权名称", type = "string",defaultValue = "无", example = "华为云北京站点运维面认证授权配置")
    private String authName;
    @Schema(description = "云平台认证授权描述", type = "string",defaultValue = "无", example = "可观测项目获取指标数据专用")
    private String authDesc;

}
