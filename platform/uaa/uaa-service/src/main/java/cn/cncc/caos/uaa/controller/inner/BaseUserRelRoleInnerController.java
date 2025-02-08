package cn.cncc.caos.uaa.controller.inner;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUserRelRole;
import cn.cncc.caos.uaa.db.dao.BaseRoleDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseRoleMapper;
import cn.cncc.caos.uaa.db.dao.BaseUserRelRoleMapper;
import cn.cncc.caos.uaa.service.BaseUserRelRoleService;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;


@Slf4j
// @RestController
@MojitoSchema(schemaId = "inner_api/baseUserRelRoleInnerController")
public class BaseUserRelRoleInnerController {

  @Autowired
  private BaseRoleMapper baseRoleMapper;

  @Autowired
  private BaseUserRelRoleService baseUserRelRoleService;

  @Autowired
  private BaseUserRelRoleMapper baseUserRelRoleMapper;

  private String operTypeUpdate = "roleRelUserUpdate";

  // @RequestMapping(value = "/inner_api/role_rel", method = RequestMethod.POST)
  public JwResponseData<Map<String, String>> getUsersByRoles(
      @RequestBody List<String> roleNameList
  ) {
    try {
      if (CollectionUtils.isEmpty(roleNameList)) {
        log.error("getUserByRoles roleNameList is empty");
        return JwResponseData.success("参数为空");
      }

      // 首先获取用户组
      List<BaseRole> baseRoles = baseRoleMapper.selectByExample()
          .where(BaseRoleDynamicSqlSupport.roleName, isIn(roleNameList))
          .and(BaseRoleDynamicSqlSupport.isValid, isEqualTo(1))
          .build().execute();

      if (CollectionUtils.isEmpty(baseRoles)) {
        log.error("getUserByRoles baseRoles is empty");
        return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs("用户组异常，请检查"));
      }

      // 根据用户组id获取关联关系
      // 将关联关系中的用户获取到，联合查询，获取用户名
      List<Integer> roleIdList = new ArrayList<>();
      for (BaseRole baseRole : baseRoles)
        roleIdList.add(baseRole.getId());

      Map<String, String> roleAndUserName = baseUserRelRoleService.getUserAndRoleByRoleId(roleIdList);
      return JwResponseData.success("获取成功", roleAndUserName);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  /***
   * 根据userid获取用户组
   * @param userId
   * @return
   */
  // @RequestMapping(value = "/inner_api/role_rel/get_role", method = RequestMethod.POST)
  public JwResponseData<List<BaseRole>> getRolesByUserId(@RequestParam(value = "userId") Integer userId) {
    try {
      List<BaseRole> baseRoleList = baseUserRelRoleService.getBaseRolesByUserId(userId);
      return JwResponseData.success("获取成功", baseRoleList);
    } catch (Exception e) {
      log.error("获取失败", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  // @RequestMapping(value = "/inner_api/role_rel/add_one", method = RequestMethod.POST)
  public JwResponseData<String> addOneRole(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "roleId") Integer roleId) {
    try {
      BaseUserRelRole burr = new BaseUserRelRole();
      burr.setUserId(userId);
      burr.setRoleId(roleId);
      burr.setIsValid(1);
      burr.setUpdateTime(TimeUtil.getCurrentTime());
      baseUserRelRoleMapper.insert(burr);
      return JwResponseData.success("新增用户角色对应关系成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  public JwResponseData<String> handoverRole(@RequestParam(value = "handoverUserId") Integer handoverUserId,
                                             @RequestParam(value = "candidateUserId") Integer candidateUserId) {
    try {
      //查询交接人所有的用户组
      List<BaseRole> handoveBaseRoleList = baseUserRelRoleService.getBaseRolesByUserId(handoverUserId);
      //查询受让人所有用户组
      List<BaseRole> candidateBaseRoleList = baseUserRelRoleService.getBaseRolesByUserId(candidateUserId);
      List<Integer> roleIDs = new ArrayList<>();
      for (BaseRole baseRole : candidateBaseRoleList) {
        if (baseRole == null) {
          continue;
        }
        roleIDs.add(baseRole.getId());
      }
      List<Integer> needHandoverRoleIdList = new ArrayList<>();
      for (BaseRole baseRole : handoveBaseRoleList) {
        if (!roleIDs.contains(baseRole.getId())) {
          needHandoverRoleIdList.add(baseRole.getId());
        }
      }
      if (needHandoverRoleIdList.isEmpty()) {
        return JwResponseData.success("交接成功", null);
      }
      List<BaseUserRelRole> baseUserRelRoleList = baseUserRelRoleService.selectByUserAndRoleIds(handoverUserId, needHandoverRoleIdList);
      baseUserRelRoleService.batchDelete(baseUserRelRoleList);
      List<BaseUserRelRole> baseUserRelRoles = new ArrayList<>();
      for (BaseUserRelRole baseUserRelRole : baseUserRelRoleList) {
        BaseUserRelRole burr = new BaseUserRelRole();
        burr.setUserId(candidateUserId);
        burr.setUpdateTime(new Date());
        burr.setRoleId(baseUserRelRole.getRoleId());
        burr.setIsValid(1);
        baseUserRelRoles.add(burr);
      }
      baseUserRelRoleService.batchInsertWithoutSync(baseUserRelRoles);
      return JwResponseData.success("交接成功", null);
    } catch (Exception e) {
      log.error("交接失败", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR.fillArgs("交接失败"));
    }
  }
}

