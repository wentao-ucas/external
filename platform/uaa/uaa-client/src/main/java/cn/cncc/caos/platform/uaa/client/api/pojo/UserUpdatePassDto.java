package cn.cncc.caos.platform.uaa.client.api.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdatePassDto {

  @Schema(description = "账号", type = "String", allowableValues = "30", required = true, example = "zhangsan")
  private String userName;

  @Schema(description = "密码, 解密后的密码", type = "String", allowableValues = "100", required = true, example = "$2a$10$Stn7UO7RkoJ3WwQ0YPuWROfAp0w30zqNfdjH7Zw0oX9H4A60wEtjG")
  private String password;

}
