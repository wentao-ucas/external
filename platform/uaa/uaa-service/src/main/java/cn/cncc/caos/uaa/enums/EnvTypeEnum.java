package cn.cncc.caos.uaa;

public enum EnvTypeEnum {
  ALL("all"),
  OFFICE("office"),
  NPC("npc");

  public final String type;

  EnvTypeEnum(String type) {
    this.type = type;
  }

  public static EnvTypeEnum typeOf(String type) {
    for (EnvTypeEnum anEnum : EnvTypeEnum.values()) {
      if (anEnum.type.equals(type)) return anEnum;
    }
    return null;
  }

  public static boolean hashPermission(String dbEnv, String jwEnv) {
    return EnvTypeEnum.ALL.type.equals(dbEnv)
        || (EnvTypeEnum.OFFICE.type.equals(dbEnv) && dbEnv.equals(jwEnv))
        || (EnvTypeEnum.NPC.type.equals(dbEnv) && jwEnv.startsWith(dbEnv));
  }
}
