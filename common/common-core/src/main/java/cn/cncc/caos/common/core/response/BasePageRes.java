package cn.cncc.caos.common.core.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "公共参数-分页返回参数")
public class BasePageRes<T> {

  @Schema(title = "总条数", description = "总条数", type = "Integer")
  private long total;

  @Schema(title = "具体内容", description = "具体内容", type = "List")
  private List<T> list;

  public BasePageRes(long total, List<T> list) {
    this.total = total;
    this.list = list;
  }

  public BasePageRes() {
  }
}
