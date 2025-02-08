package cn.cncc.caos.uaa.controller.inner;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.db.daoex.BaseRoleMapperEx;
import cn.cncc.caos.uaa.db.daoex.BaseUserMapperEx;
import cn.cncc.caos.uaa.utils.EncryptAndDecryptUtil;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
// @RestController
@MojitoSchema(schemaId = "inner_api/baseRoleRelRoleInnerController")
public class BaseRoleRelRoleInnerController {
  @Autowired
  private BaseUserMapperEx baseUserMapperEx;

  @Autowired
  private BaseRoleMapperEx baseRoleMapperEx;

//    @ResponseBody
//    // @RequestMapping("/api/role_rel_role/get_role_list_by_role_id_key_and_usage")
//    public JwResponseData<List<BaseRole>> getRoleListByRoleIdKeyAndUsage(@RequestParam(value = "roleIdKey", required = true) Integer roleIdKey,
//            @RequestParam(value = "roleRelUsage", required = true) String roleRelUsage) {
//        List<BaseRole>  list = baseRoleMapperEx.getRoleListByRoleIdKey(roleIdKey,roleRelUsage);
//        return JwResponseData.success("获得角色列表成功",list);
//    }

  @ResponseBody
  // @RequestMapping("/api/role_rel_role/get_user_list_by_role_id_key_and_usage")
  public JwResponseData<List<BaseUser>> getUserListByRoleIdKeyAndUsage(@RequestParam(value = "roleIdKey", required = true) Integer roleIdKey,
                                                                       @RequestParam(value = "roleRelUsage", required = true) String roleRelUsage) {
    try {
      List<BaseUser> list = baseUserMapperEx.getUserListByRoleIdKey(roleIdKey, roleRelUsage);
      for (BaseUser baseUser : list) {
        //解密
        EncryptAndDecryptUtil.decryptBaseUser(baseUser);
      }
      return JwResponseData.success("获得用户列表成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
}

