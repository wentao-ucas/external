package cn.cncc.caos.uaa.controller.inner;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.service.BaseRoleAuthService;
import cn.cncc.caos.uaa.service.BaseUserRoleAuthService;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRoleAuth;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
// @RestController
// @Tag(name = "关于角色安全相关")
@MojitoSchema(schemaId = "inner_api/baseRoleAuthInnerController")
public class BaseRoleAuthInnerController {

  @Autowired
  private BaseRoleAuthService baseRoleAuthService;

  @Autowired
  private BaseUserRoleAuthService baseUserRoleAuthService;

  // @RequestMapping(value = "/inner_api/role/auth/user/id", method = RequestMethod.GET)
  public JwResponseData<List<BaseRoleAuth>> getRoleAuthByUserId(@RequestParam(value = "userId") int userId) {
    try {
      List<BaseRoleAuth> baseRoleListJwResponseData = baseRoleAuthService.getRoleByUserId(userId);
      return JwResponseData.success("getUserByUserId获取成功", baseRoleListJwResponseData);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  // @RequestMapping(value = "/inner_api/role/auth/handover_role_auth", method = RequestMethod.GET)
  public JwResponseData<String> handoverRoleAuth(@RequestParam(value = "handoverUserId") Integer handoverUserId,
                                               @RequestParam(value = "candidateUserId") Integer candidateUserId) {
    try {
      //查询用户所有关联角色
      List<BaseRoleAuth> handoverRoleAuthList = baseRoleAuthService.getRoleByUserId(handoverUserId);
      List<String> handoverRoleAuthIdList = new ArrayList<>();
      for (BaseRoleAuth baseRoleAuth : handoverRoleAuthList) {
        handoverRoleAuthIdList.add(baseRoleAuth.getId());
      }
      //查询出role——>user映射关系
      HashMap<String, List<Integer>> handoverRoleUserMap = baseUserRoleAuthService.selectUsersByRoleAuthIdToMap(handoverUserId,handoverRoleAuthIdList);
      if (handoverRoleUserMap.isEmpty()) {
        return JwResponseData.success("交接成功",null);
      }
      handoverRoleUserMap.forEach((k,v)->{
        if (!v.contains(candidateUserId)) { //如果某角色不包含候选人
          baseUserRoleAuthService.handoverRoleAuth(k,handoverUserId,candidateUserId);
        }
      });
      baseUserRoleAuthService.unboundRoleAuth(handoverUserId);
      return JwResponseData.success("交接成功",null);
    } catch (Exception e) {
      log.error("交接失败",e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR.fillArgs("交接失败"));
    }
  }

}
