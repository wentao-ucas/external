package cn.cncc.caos.uaa.controller.inner;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.controller.base.BaseBaseRoleController;
import cn.cncc.caos.uaa.db.dao.BaseRoleDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseRoleMapper;
import cn.cncc.caos.uaa.db.daoex.BaseRoleMapperEx;
import cn.cncc.caos.uaa.service.BaseRoleService;
import cn.cncc.mojito.rpc.provider.annotation.MojitoSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Slf4j
// @RestController
@MojitoSchema(schemaId = "inner_api/baseRoleInnerController")
public class BaseRoleInnerController extends BaseBaseRoleController {

  @Autowired
  private ServerConfigHelper serverConfigHelper;
  @Autowired
  private BaseRoleMapperEx baseRoleMapperEx;

  @Autowired
  private BaseRoleService baseRoleService;
  @Autowired
  private BaseRoleMapper baseRoleMapper;

  // @RequestMapping(value = "/inner_api/role/get_role_by_user_id", method = RequestMethod.GET)
  public JwResponseData<List<BaseRole>> getRoleByUserId(@RequestParam(value = "userId", required = true) int userId) {
    try {
      List<BaseRole> baseRoleListJwResponseData = baseRoleMapperEx.getRoleByUserId(userId);
      return JwResponseData.success("getUserByUserId获取成功", baseRoleListJwResponseData);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }


  // @RequestMapping(value = {"/inner_api/role/get_operation_dep_role"}, method = RequestMethod.GET)
  public JwResponseData<List<BaseRole>> getOperationDepRole() {
    return super.getOperationDepRole();
  }

  // @RequestMapping(value = "/inner_api/role/get_operation_dep_role_rel_user", method = RequestMethod.GET)
  public JwResponseData<List<Map<String, Object>>> getOperationDepRoleRelUser() {
    try {
      List<Map<String, Object>> list = baseRoleMapperEx.getRoleAndUsersByRoleKey(serverConfigHelper.getValue("roleOperationDep"));
//      log.info(JSONArray.toJSONString(list));
      return JwResponseData.success("根据系统获得角色和人员对应列表成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  // @RequestMapping(value = {"/inner_api/admin/role/get_all"})
  public JwResponseData<List<BaseRole>> getAllRole() {
    return super.getAllRole();
  }

  @ResponseBody
  // @RequestMapping(value = "/api/admin/role/get_by_dep_id", method = RequestMethod.GET)
  public JwResponseData<List<String>> getRoleListByDepId(@RequestParam(value = "depId", required = true) String depId) {
    try {
      return JwResponseData.success("根据部门ID获得角色名称列表成功", baseRoleService.getRoleNameByDepid(depId));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
  // @RequestMapping(value = "/inner_api/role/get_role_by_sysId", method = RequestMethod.GET)
  public JwResponseData<List<BaseRole>> getRolesBySysId(@RequestParam(value = "sysId", required = true) Integer sysId) {
    try {
      List<BaseRole> baseRoleList = baseRoleMapper.selectByExample()
          .where(BaseRoleDynamicSqlSupport.sysId, isEqualTo(sysId))
          .build().execute();
      return JwResponseData.success("根据系统ID获得角色列表成功", baseRoleList);
    } catch (Exception e) {
      log.error("根据系统ID获得角色列表失败", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  //  // @RequestMapping(value = "/inner_api/base/role/operation/dep/role/by/user", method = RequestMethod.GET)
  public JwResponseData<List<BaseRole>> getOperationDepRoleByUserId(@RequestParam(value = "userId") Integer userId) {
    List<BaseRole> baseRoleList = baseRoleService.getOperationDepRoleByUserId(userId);
    return JwResponseData.success("获取成功", baseRoleList);
  }
}

