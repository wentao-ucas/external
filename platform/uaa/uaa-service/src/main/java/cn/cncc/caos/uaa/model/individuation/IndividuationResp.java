package cn.cncc.caos.uaa.model.individuation;

import lombok.Data;

import java.util.List;

/**
 * @description 配置交互实体类.
 * @date 2022/11/02 15:47.
 */
@Data
public class IndividuationResp {

  /* 模块儿唯一id */
  private String moduleId;
  /* 模块儿名称 */
  private String moduleName;
  /* 当前模块儿父系统id */
  private String systemId;
  /* 邮件状态 */
  private byte mailStatus;
  /* 短信状态 */
  private byte smsStatus;
  /* 当前系统子模块儿 */
  private List<IndividuationResp> children;

}
