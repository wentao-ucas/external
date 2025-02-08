package cn.cncc.caos.common.core.response.jw;

public class JwResponseCode {

  private int code;

  private String msg;

  //通用的错误码
  public static JwResponseCode SUCCESS = new JwResponseCode(1, "success");

  public static JwResponseCode SERVER_ERROR = new JwResponseCode(500100, "服务端异常");
  public static JwResponseCode BIND_ERROR = new JwResponseCode(500101, "参数校验异常：%s");
  public static JwResponseCode ACCESS_DENIED_ERROR = new JwResponseCode(500102, "非授权访问");

  public static JwResponseCode DuplicateKeyException = new JwResponseCode(500102, "java向数据库插入数据异常: DuplicateKeyException");

  public static JwResponseCode SERVICE_NOT_AVAILABLE = new JwResponseCode(500103, "service not available");
  public static JwResponseCode DOWNLOAD_FILE_ERROR = new JwResponseCode(500104, "下载文件失败");
  public static JwResponseCode TIME_PARSE_ERROR = new JwResponseCode(500105, "时间转换失败");
  public static JwResponseCode ERROR_OPERATE = new JwResponseCode(500106, "操作失败");
  public static JwResponseCode COMPLETE_USER_ERROR = new JwResponseCode(500110, "当前处理人无操作权限");

  public static JwResponseCode COLUMN_DUPLICATE = new JwResponseCode(500111, "该字段已存在");

  public static JwResponseCode FILE_EXIST = new JwResponseCode(500112, "该文件不存在");


  public static JwResponseCode CQ_ID_DUPLICATE = new JwResponseCode(600001, "CQ单id已存在，请修改后上传，重复id:%s");

  public static JwResponseCode UPDATE_ERROR = new JwResponseCode(500113, "更新失败:%s");

  public static JwResponseCode INSERT_ERROR = new JwResponseCode(500114, "新增失败:%s");

  public static JwResponseCode COMPLETE_ERROR = new JwResponseCode(500115, "处理失败:%s");

  public static JwResponseCode DELETE_ERROR = new JwResponseCode(500116, "删除失败:%s");

  public static JwResponseCode PERMISSION_PARENT_ERROR = new JwResponseCode(500117, "权限父目录异常:%s");

  public static JwResponseCode NAME_DUPLICATE = new JwResponseCode(500118, "唯一标志重复:%s");

  public static JwResponseCode DB_DATA_NOT_EXIST = new JwResponseCode(500119, "数据不存在");

  public static JwResponseCode RULE_PARENT_ERROR = new JwResponseCode(500120, "规则父目录异常:%s");

  public static JwResponseCode UPLOAD_CQ_ERROR = new JwResponseCode(500121, "cq单上传异常:%s");

  public static JwResponseCode SEND_MSG_ERROR = new JwResponseCode(500122,"信息发送异常:%s");

  public static JwResponseCode GET_LOGIN_USER_ERROR = new JwResponseCode(500123,"获取当前登录用户异常");

  public static JwResponseCode REVOKE_TASK_ERROR = new JwResponseCode(500124,"撤回节点异常:%s");

  public static JwResponseCode REQUIRE_FEEDBACK_ERROR = new JwResponseCode(500125,"请求反馈单异常");

  public static JwResponseCode FUNCTION_NOT_OPEN = new JwResponseCode(500126,"该功能暂未开放");

  public static JwResponseCode DOWNLOAD_TEMPLATE_ERROR = new JwResponseCode(500127, "下载模板失败:%s");

  public static JwResponseCode EXCEL_TEMPLATE_ERROR = new JwResponseCode(500128, "excel模板异常:%s");

  public static JwResponseCode USER_ERROR = new JwResponseCode(500129, "用户已存在");

  public static JwResponseCode USER_NOT_ERROR = new JwResponseCode(500130, "用户不存在");

  public static JwResponseCode LOGIN_ERROR = new JwResponseCode(500130, "登录跳转失败");

  public static JwResponseCode UOMP_USER_NOT_ERROR = new JwResponseCode(500130, "精卫不存在此用户");
  public static JwResponseCode CONFIG_NOT_FIND = new JwResponseCode(500131, "该配置不存在:%s");
  public static JwResponseCode PUSH_ERROR = new JwResponseCode(500132, "推送外部系统失败");
  public static JwResponseCode UPDATE_PASSWORD_ERROR = new JwResponseCode(500133, "更新密码失败");

  public static JwResponseCode RPC_ERROR = new JwResponseCode(500134, "请求其他服务失败:%s");
  public Integer getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  public JwResponseCode fillArgs(Object... args) {
    int code = this.code;
    String message = String.format(this.msg, args);
    return new JwResponseCode(code, message);
  }

  protected JwResponseCode(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public static JwResponseCode columnDuplicate(int num, String key) {
    return new JwResponseCode(500107, "第" + num + "行," + key + "字段已存在");
  }

  public static JwResponseCode COLUMN_NOT_EXIST(int num, String key) {
    return new JwResponseCode(500108, "第" + num + "行," + key + "字段不存在");
  }

  public static JwResponseCode COLUMN_IS_NULL(int columnNum, String stringCellValue2) {
    return new JwResponseCode(500109, "第" + columnNum + "行，" + stringCellValue2 + "字段不能为空");
  }


}

