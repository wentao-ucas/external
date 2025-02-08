package cn.cncc.caos.common.core.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BapRequest<T> extends BaseRequest<T> {
  @Schema(description = "客户端ID", type = "String", example = "fc4afa6c-bac0-b65d-9758-2100add12964", defaultValue = "fc4afa6c-bac0-b65d-9758-2100add12964")
  private String client;
}
