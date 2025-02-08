package cn.cncc.caos.uaa.controller.base;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.daoex.BaseRoleMapperEx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.cncc.caos.uaa.db.dao.BaseUserRelRoleDynamicSqlSupport.isValid;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Slf4j
//@RestController
public abstract class BaseBaseRoleController {

  @Autowired
  private ServerConfigHelper serverConfigHelper;
  @Autowired
  private BaseRoleMapperEx baseRoleMapperEx;

  //  @RequestMapping(value = {"/inner_api/role/get_operation_dep_role", "/open_api/role/get_operation_dep_role"}, method = RequestMethod.GET)
  public JwResponseData<List<BaseRole>> getOperationDepRole() {
    try {
// TODO 值班辅助系统调用超时，查原因
      List<BaseRole> list = baseRoleMapperEx.getRoleByRoleKey(serverConfigHelper.getValue("roleOperationDep"));
      return JwResponseData.success("根据系统获得角色成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @ResponseBody
//  @RequestMapping(value = {"/api/admin/role/get_all", "/inner_api/admin/role/get_all"})
  public JwResponseData<List<BaseRole>> getAllRole() {
    try {
      List<BaseRole> list = baseRoleMapperEx.selectByExample().where(isValid, isEqualTo(1)).build().execute();
      return JwResponseData.success("获得所有角色成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }


}

