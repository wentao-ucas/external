package cn.cncc.caos.uaa.enums;

public enum InstanceTypeEnum {
  ALL("all"),
  CNCC("cncc"),
  CFID("cfid");

  public final String type;

  InstanceTypeEnum(String type) {
    this.type = type;
  }

  public static InstanceTypeEnum typeOf(String type) {
    for (InstanceTypeEnum anEnum : InstanceTypeEnum.values()) {
      if (anEnum.type.equals(type)) return anEnum;
    }
    return null;
  }
}
