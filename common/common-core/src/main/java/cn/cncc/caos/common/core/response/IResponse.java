package cn.cncc.caos.common.core.response;


import cn.cncc.caos.common.core.annotation.ErrorDescription;

public interface IResponse<T> {
  @ErrorDescription("执行成功")
  String SUC_CODE = "0";

  @ErrorDescription("执行完成，但是存在部分失败")
  String PARTIAL_SUC_CODE = "0001";
  @ErrorDescription("系统异常")
  String ERR_CODE = "9999";
  @ErrorDescription("参数异常")
  String ERR_PARAMS = "1001";
  @ErrorDescription("未登陆")
  String ERR_LOGIN = "1002";
  @ErrorDescription("查无数据")
  String ERR_DATA = "1003";
  @ErrorDescription("数据重复，不可操作")
  String ERR_DUPLICATE = "1004";
  @ErrorDescription("请填写正确的有效起止时间")
  String ERR_VALIDATE_DATE = "1005";
  @ErrorDescription("任务进行中，不可重复操作")
  String ERR_DUPLICATE_TASK = "1006";
  @ErrorDescription("执行失败")
  String ERR_EXECUTE = "1007";
}
