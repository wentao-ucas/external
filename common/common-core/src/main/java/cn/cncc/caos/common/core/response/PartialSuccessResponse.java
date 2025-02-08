package cn.cncc.caos.common.core.response;

public class PartialSuccessResponse<T> extends BaseResponse<T> {
  public final static IResponse<String> DEFAULT = new PartialSuccessResponse<>();

  public PartialSuccessResponse() {
    this(null);
  }

  public PartialSuccessResponse(T data) {
    super(PARTIAL_SUC_CODE, data, null);
  }
}