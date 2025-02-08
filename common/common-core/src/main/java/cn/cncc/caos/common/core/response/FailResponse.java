package cn.cncc.caos.common.core.response;

import cn.cncc.caos.common.core.exception.BapLogicException;
import cn.cncc.caos.common.core.exception.BapParamsException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "失败消息对象")
@Getter
@Setter
public class FailResponse<T> extends BaseResponse<T> {


  public FailResponse(String code) {
    this(code, null);
  }

  public FailResponse(String code, String extraMsg) {
    super(code, null, extraMsg);
  }

  public static <T> FailResponse<T> getResponse(Exception e) {
    String code;
    if (e instanceof BapParamsException) {
      code = IResponse.ERR_PARAMS;
    } else if (e instanceof BapLogicException) {
      code = IResponse.ERR_EXECUTE;
    } else {
      code = IResponse.ERR_CODE;
    }
    return new FailResponse<>(code, e.getMessage());
  }

}
