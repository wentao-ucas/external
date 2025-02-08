package cn.cncc.caos.uaa.enums;

import lombok.Getter;

/**
 * @description 消息中心功能-通用系统标识.
 * 注:与UserCommentSystemEnum.java枚举类"systemId"保持一致但使用情况不一样,暂时没有使用到可以先注释,增加时两个枚举同步增加
 * @date 2023/8/21 8:32.
 */
@Getter
public enum McMessagesSystemEnum {
  PROCESS_MANAGE((byte) 1, "流程平台"),
  OPERATION_ASSIST((byte) 2, "值班辅助系统"),
  PROCESS_ASSIST((byte) 3, "流程小助手"),
//  DOC_LIB((byte) 4, "文档库"), // 暂时未涉及
  ;

  public final byte systemId;
  public final String systemName;

  McMessagesSystemEnum(byte systemId, String systemName) {
    this.systemId = systemId;
    this.systemName = systemName;
  }

  public static McMessagesSystemEnum getSystemBySystemId(byte systemId) {
    for (McMessagesSystemEnum system : McMessagesSystemEnum.values()) {
      if (system.systemId == systemId) return system;
    }
    return null;
  }

  public static McMessagesSystemEnum getSystemBySystemName(String systemName) {
    for (McMessagesSystemEnum system : McMessagesSystemEnum.values()) {
      if (system.systemName.equals(systemName)) return system;
    }
    return null;
  }

}
