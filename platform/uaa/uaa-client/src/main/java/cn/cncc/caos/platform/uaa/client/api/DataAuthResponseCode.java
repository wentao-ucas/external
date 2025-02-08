package cn.cncc.caos.platform.uaa.client.api;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;

public class DataAuthResponseCode extends JwResponseCode {
  //调用成功为0，-1为服务不可用
  public static DataAuthResponseCode SERVICE_NOT_AVAILABLE = new DataAuthResponseCode(-1, "data-auth-privider服务不可用");

  public static DataAuthResponseCode PASSWORD_EMPTY = new DataAuthResponseCode(500211, "登录密码不能为空");
  public static DataAuthResponseCode LOGIN_USER_NOT_EXIST = new DataAuthResponseCode(100100, "用户名或密码错误，请重新输入");
  public static DataAuthResponseCode LOGIN_USER_PASSWORD_ERROR = new DataAuthResponseCode(100101, "登录用户的密码不正确");
  public static DataAuthResponseCode LOGIN_USER_CALL_AUTH_OAUTH_FAIL = new DataAuthResponseCode(100102, "登录用户时授权中心获取TOKEN失败");
  public static DataAuthResponseCode LOGIN_USER_LOCKED = new DataAuthResponseCode(100103, "用户被锁定，请联系管理员解决");
  public static DataAuthResponseCode VERIFICATION_CODE_NOT_EXIST = new DataAuthResponseCode(100104, "验证码失效，请重新获取");
  public static DataAuthResponseCode VERIFICATION_CODE_FALSE = new DataAuthResponseCode(100105, "验证码错误，请重新输入");
  public static DataAuthResponseCode PHONE_NOT_EXIST = new DataAuthResponseCode(100106, "手机号不存在");
  public static DataAuthResponseCode LOGIN_USER_LOCKED_TEMP = new DataAuthResponseCode(100108, "用户被锁定，请稍后重试");
  public static DataAuthResponseCode CODE_HAVE_TIME = new DataAuthResponseCode(100109, "验证码已发送，请勿重复点击");

  public static DataAuthResponseCode NOT_AUTHORIZED_GET_INFO_FROM_OTHER_USER = new DataAuthResponseCode(500213, "未经授权访问其他用户相关资料");
  public static DataAuthResponseCode DEP_NOT_DELETE_BECAUSE_HAS_CHILDREN = new DataAuthResponseCode(500314, "因为存在未删除的子部门所以无法删除本部门");
  public static DataAuthResponseCode ROLE_NOT_DELETE_BECAUSE_HAS_USER = new DataAuthResponseCode(500315, "因为该角色下还存在用户所以无法删除本角色");
  public static DataAuthResponseCode SYS_NOT_DELETE_BECAUSE_HAS_ROLE = new DataAuthResponseCode(500316, "因为该系统下还存在角色所以无法删除本系统");
  public static DataAuthResponseCode ROLE_NOT_DELETE_BECAUSE_HAS_PERMISSION = new DataAuthResponseCode(500317, "因为该角色下还存在权限所以无法删除本角色");

  //public static DataAuthResponseCode USER_ADD_SUCEESS = new DataAuthResponseCode(500310, "用户新建成功");

  private DataAuthResponseCode(int code, String msg) {
    super(code, msg);
  }

  public static DataAuthResponseCode errorNumbers(int useNumbers, int numbers) {
    return new DataAuthResponseCode(100107, "用户名或密码错误，连续输错"+useNumbers+"次，仅剩" + numbers + "次机会");
  }

}
