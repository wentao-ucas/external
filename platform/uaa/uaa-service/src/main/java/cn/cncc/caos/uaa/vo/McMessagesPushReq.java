package cn.cncc.caos.uaa.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @description 消息中心-推送消息实体.
 * @date 2023/8/18 13:44.
 */
@Setter
@Getter
public class McMessagesPushReq {

  private Integer userId;

  private String userRealName;

  private Byte system;

  private Byte function;

  private String title;

  private Byte isCanSkip;

  private String content;

  private String params;

}
