package cn.cncc.caos.uaa.model.base.post;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasePostListInfoWithTotal {
  private Long total;
  private List<BasePostRes> list;
}
