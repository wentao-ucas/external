package cn.cncc.caos.uaa.model.individuation;

import lombok.Data;

/**
 * @description 用户个性化修改配置请求体.
 * @date 2022/11/03 14:06.
 */
@Data
public class AddOrUpdateIndividuationReq {

  /* 用户唯一id */
  private Integer userId;
  /* 本次修改后的用户配置 */
  private IndividuationResp configInfo;
}
