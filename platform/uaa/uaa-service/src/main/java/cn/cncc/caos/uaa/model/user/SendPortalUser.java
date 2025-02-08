package cn.cncc.caos.uaa.model.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendPortalUser {

  // 标识id
  private String client_id;

  // 密码
  private String client_secret;

  // 变更类型(CC01：变更)
  private String changecode;

  // 被纳管loginid
  private String loginid;

  // 用户名
  private String uname;

  // 手机号
  private String tel;

  // 部门编号
  private String deptcode;

}
