package cn.cncc.caos.uaa.enums;

public enum LocationTypeEnum {
  ALL("all"),
  NPC_BJ("npc-bj"),
  NPC_SH("npc-sh"),
  ;

  public final String type;

  LocationTypeEnum(String type) {
    this.type = type;
  }

  public static LocationTypeEnum typeOf(String type) {
    for (LocationTypeEnum anEnum : LocationTypeEnum.values()) {
      if (anEnum.type.equals(type)) return anEnum;
    }
    return null;
  }
}
