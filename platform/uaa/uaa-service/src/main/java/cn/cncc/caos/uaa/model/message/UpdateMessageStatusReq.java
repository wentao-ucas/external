package cn.cncc.caos.uaa.model.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @description 更新单个消息-请求体.
 * @date 2023/8/18 15:59.
 */
@Setter
@Getter
public class UpdateMessageStatusReq {

  @NotEmpty
  @Schema(description = "X1234567", type = "String", required = true, example = "X1234567")
  private String id;
  @NotEmpty
  @Schema(description = "1", type = "byte", required = true, example = "1:已读 2:未读")
  private byte status;

}
