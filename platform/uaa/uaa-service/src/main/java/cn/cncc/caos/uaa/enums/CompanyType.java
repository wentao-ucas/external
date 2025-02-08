package cn.cncc.caos.uaa.enums;

public enum CompanyType {
  ZONG_ZHONG_XIN(1, "总中心"),
  JIN_XIN(2, "金信"),
  JIN_KE(3, "金科"),
  SHIYE(4, "事业"),
  YINQIN(5, "银清"),
  WAIBAO(6,"外包");
  public final int index;
  public final String name;

  CompanyType(int index, String name) {
    this.index = index;
    this.name = name;
  }

  public static CompanyType getCompanyTypeByName(String name) {
    for (CompanyType companyType : CompanyType.values()) {
      if (companyType.name.equals(name)) return companyType;
    }
    return null;
  }

  public static String companyTypeToName(String companyName){
    CompanyType companyType = getCompanyTypeByName(companyName);
    switch (companyType) {
      case SHIYE:
      case ZONG_ZHONG_XIN:
        return CompanyType.SHIYE.name;
      case YINQIN:
      case JIN_XIN:
      case JIN_KE:
        return CompanyType.YINQIN.name;
    }
    return null;
  }
}
