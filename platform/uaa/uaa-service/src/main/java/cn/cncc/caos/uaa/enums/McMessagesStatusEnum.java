package cn.cncc.caos.uaa.enums;

import lombok.Getter;

@Getter
public enum McMessagesStatusEnum {
  READ((byte) 1, "已读"),
  UN_READ((byte)2, "未读");

  public final byte index;
  public final String name;

  McMessagesStatusEnum(byte index, String name) {
    this.index = index;
    this.name = name;
  }

  public static McMessagesStatusEnum getStatusByName(String name) {
    for (McMessagesStatusEnum type : McMessagesStatusEnum.values()) {
      if (type.name.equals(name)) return type;
    }
    return null;
  }

}
