package cn.cncc.caos.uaa.model.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @description 批量更新消息状态-请求体.
 * @date 2023/8/18 16:01.
 */
@Setter
@Getter
public class BatchUpdateMessageStatusReq {

  @NotEmpty
  @Schema(description = "605", type = "Integer", required = true, example = "605")
  private Integer userId;
  @NotEmpty
  @Schema(description = "张三", type = "String", required = true, example = "张三")
  private String userRealName;
  @NotEmpty
  @Schema(description = "1", type = "byte", required = true, example = "1:流程平台 2:值班辅助系统 3:流程小助手")
  private String systemName;

}
