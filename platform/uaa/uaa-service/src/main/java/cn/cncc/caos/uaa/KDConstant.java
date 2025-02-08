package cn.cncc.caos.uaa;

import java.time.format.DateTimeFormatter;

public interface KDConstant {
  String PHONE_DEFAULT = "11111111111";
  String TIPS_NOT_LOGIN = "未登录";

  DateTimeFormatter DFT_DEFAULT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  String TIME_RANGE_TYPE_INSIDE = "inside";
  String TIME_RANGE_TYPE_OUTSIDE = "outside";

  //默认页
  int PAGE_DEFAULT = 1;
  //默认每页列表数据
  int PAGE_SIZE_DEFAULT = 1000;

}
