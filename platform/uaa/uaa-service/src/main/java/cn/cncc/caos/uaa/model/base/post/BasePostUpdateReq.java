package cn.cncc.caos.uaa.model.base.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BasePostUpdateReq {

  @NotEmpty
  private String id;
  @NotEmpty
  private String postCode;
  @NotEmpty
  private String postName;

  private Integer postSort;
  @NotNull
  private Byte status;

  private String remark;
}
