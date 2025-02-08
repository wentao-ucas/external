package cn.cncc.caos.common.core.response;

import cn.cncc.caos.common.core.utils.BaseResponseUtil;
import cn.cncc.caos.common.core.utils.StringUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public abstract class BaseResponse<T> implements IResponse<T> {

  @Schema(description = "状态码", type = "string", example = "1001", defaultValue = "1001")
  private String code;
  @Schema(description = "接口响应时间（秒）", type = "integer", example = "123", defaultValue = "123")
  private int cost;
  private T data;
  @Schema(description = "提示信息", type = "string", example = "操作成功", defaultValue = "操作成功")
  private String msg;

  public BaseResponse(String code, T data, String extraMsg) {
    String msg = BaseResponseUtil.getResponseErrMap().get(code);
    if (StringUtil.isNotEmpty(extraMsg)) {
      msg += "; " + extraMsg;
    }
    this.msg = msg;
    this.code = code;
    this.data = data;
  }
}