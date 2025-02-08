package cn.cncc.caos.common.core.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public abstract class BaseRequest<T> {
  @NotNull(message = "请求数据对象不能为空")
  @Valid
  private T data;
}
