package cn.cncc.caos.platform.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "测试返回对象")
public class TestRes {
  @Schema(description = "姓名", type = "string", defaultValue = "李四", example = "李四")
  private String name;
  @Schema(description = "性别", type = "string", defaultValue = "男", example = "男")
  private String sex;
  @Schema(description = "地址", type = "string", defaultValue = "北京市", example = "北京市")
  private String address;
  private Pet pet;

  @Getter
  @Setter
  public static class Pet {
    @Schema(description = "宠物名", type = "string", defaultValue = "AA", example = "AA")
    private String name;
    @Schema(description = "宠物类型", type = "string", defaultValue = "猫", example = "猫")
    private String type;
  }
}
