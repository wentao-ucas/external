package cn.cncc.caos.uaa.model.rule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BaseRuleUpdateReq extends BaseRuleReq{
  @NotBlank(message = "id不能为空")
  @Schema(description = "id", type = "String", required = true, example = "123")
  private String id;
}
