package cn.cncc.caos.uaa.model.message;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @description 批量更新消息状态-请求体.
 * @date 2023/10/08 9:14.
 */
@Setter
@Getter
public class BatchUpdateMessageStatusDataSyncVo {

  private Integer userId;
  private byte systemId;
  private Date updateTime;

}
