package cn.cncc.caos.platform.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Schema(description = "测试请求对象")
public class TestReq {
  @Schema(description = "主键", type = "integer", example = "1", defaultValue = "1")
  @Max(100)
  private Integer id;
  @Schema(description = "姓名", type = "string ", defaultValue = "张三", example = "张三")
  @NotEmpty(message = "姓名不能为空")
  private String name;
}
