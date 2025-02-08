package cn.cncc.caos.common.core.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "成功消息对象")
public class SuccessResponse<T> extends BaseResponse<T> {
  public final static IResponse<String> DEFAULT = new SuccessResponse<>();

  public SuccessResponse() {
    this(null);
  }

  public SuccessResponse(T data) {
    super(SUC_CODE, data, null);
  }
}