package cn.cncc.caos.common.core.enums;

/**
 * 通用的数据状态类别，如有需要也可自定义
 */
public enum DataStatus {
  DELETE((byte) 0),
  //正常
  NORMAL((byte) 1);
  public final byte index;

  DataStatus(byte index) {
    this.index = index;
  }

  public static DataStatus indexOf(byte index) {
    for (DataStatus status : DataStatus.values()) {
      if (status.index == index) return status;
    }
    return null;
  }

  public static boolean isNormal(byte index) {
    DataStatus status = DataStatus.indexOf(index);
    return DataStatus.NORMAL.equals(status);
  }
}
