package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.uaa.utils.EntityUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.*;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.controller.base.BaseBaseRoleController;
import cn.cncc.caos.uaa.db.dao.BaseSysDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseSysMapper;
import cn.cncc.caos.uaa.db.dao.BaseUserRelRoleMapper;
import cn.cncc.caos.uaa.db.daoex.BaseRoleMapperEx;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.model.role.BaseRoleRes;
import cn.cncc.caos.uaa.service.BaseRoleService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import cn.cncc.caos.platform.uaa.client.api.DataAuthResponseCode;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseDep;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseSys;
import cn.cncc.caos.uaa.utils.Tree;
import cn.cncc.caos.uaa.utils.TreeUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.cncc.caos.uaa.db.dao.BaseRoleDynamicSqlSupport.sysId;
import static cn.cncc.caos.uaa.db.dao.BaseUserRelRoleDynamicSqlSupport.isValid;
import static cn.cncc.caos.uaa.db.dao.BaseUserRelRoleDynamicSqlSupport.roleId;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;

@Slf4j
@RestController
public class BaseRoleController extends BaseBaseRoleController {

  @Autowired
  private ServerConfigHelper serverConfigHelper;
  @Autowired
  private BaseRoleMapperEx baseRoleMapperEx;

  @Autowired
  private BaseSysMapper baseSysMapper;

  @Autowired
  private BaseUserRelRoleMapper baseUserRelRoleMapper;

  private String operTypeAdd = "roleAdd";
  private String operTypeUpdate = "roleUpdate";
  private String operTypeDelete = "roleDelete";

  @Autowired
  private OperationHistoryService operationHistoryService;
  @Autowired
  private BaseRoleService baseRoleService;


//  @PreAuthorize("hasAuthority('user-group-manage')")
  @PreAuthorize("hasAnyAuthority('user-group-manage','node-info-manage')")
  @RequestMapping(value = { "/open_api/role/get_operation_dep_role"}, method = RequestMethod.GET)
  public JwResponseData<List<BaseRole>> getOperationDepRole() {
    return super.getOperationDepRole();
  }

  @PreAuthorize("hasAuthority('user-group-manage')")
  @RequestMapping(value = {"/inner_api/role/get_all_dep_role", "/open_api/role/get_all_dep_role"}, method = RequestMethod.GET)
//    @RequestMapping(value = {"/api/role/get_role_by_sys_name","/open_api/role/get_role_by_sys_name"}, method = RequestMethod.GET)
  JwResponseData<List<BaseRole>> getAllDepRole() {
    try {
      List<String> roleAllDepList = serverConfigHelper.getListValue("roleAllDep");
      List<BaseRole> list = baseRoleMapperEx.getRoleByRoleKeyArray(roleAllDepList);
      return JwResponseData.success("根据系统获得角色成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('user-group-manage')")
  @RequestMapping(value = {"/open_api/role/get_role_by_sys_name"}, method = RequestMethod.GET)
  JwResponseData<List<BaseRole>> getRoleBySysName(@RequestParam(value = "sysName", required = true) String sysName) {
    try {
      List<BaseRole> list = baseRoleMapperEx.getRoleBySysName(sysName);
      return JwResponseData.success("根据系统获得角色成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAnyAuthority('user-audit-manage')")
  @RequestMapping(value = {"/open_api/role/for/export"}, method = RequestMethod.GET)
  JwResponseData<List<BaseRole>> getExportRoleBySysName() {
    try {
      List<BaseRole> resultList = baseRoleService.getExportRoleBySysName();
      return JwResponseData.success("获取导出岗位下拉框信息成功", resultList);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }


  @PreAuthorize("hasAuthority('user-group-manage')")
  @ResponseBody
  @RequestMapping(value = {"/api/admin/role/get_all"})
  public JwResponseData<List<BaseRole>> getAllRole() {
    return super.getAllRole();
  }

  @PreAuthorize("hasAuthority('user-group-manage')")
  @ResponseBody
  @RequestMapping("/api/admin/role/get_all_by_tree")
  public JwResponseData<Map> getAllRoleTree(@RequestParam(value = "isShowRole", required = false, defaultValue = "0") int isShowRole) {

    try {
      List<BaseSys> list = baseSysMapper.selectByExample().where(BaseSysDynamicSqlSupport.isValid, isEqualTo(1)).build().execute();

      List<Tree<BaseDep>> trees = new ArrayList<Tree<BaseDep>>();

      Tree<BaseDep> rootTree = new Tree<BaseDep>();
      rootTree.setText("根节点");
      rootTree.setId("0");
      rootTree.setParentId("-1");
      rootTree.setDisabled(true);
      Map<String, Object> otherField = new HashMap<String, Object>();
      otherField.put("disabled", Boolean.TRUE);
      rootTree.setOtherField(otherField);
      rootTree.setExpand(true);
      trees.add(rootTree);

      for (BaseSys one : list) {
        Tree<BaseDep> tree = new Tree<BaseDep>();
        tree.setName(one.getSysName());
        tree.setText(one.getSysTitle());
        tree.setId("t" + one.getId().toString());
        tree.setParentId("0");
        tree.setExpand(true);
        tree.setDisabled(true);

        if (isShowRole == 1) {
          Map<String, Object> otherField2 = new HashMap<String, Object>();
          otherField2.put("disabled", Boolean.TRUE);
          tree.setOtherField(otherField2);
        }
        trees.add(tree);
      }

      if (isShowRole == 1) {
        List<BaseRole> listRole = baseRoleMapperEx.selectByExample().where(isValid, isEqualTo(1)).build().execute();
        for (BaseRole one : listRole) {
          Tree<BaseDep> tree = new Tree<BaseDep>();
          tree.setText(one.getRoleName());
          tree.setId(one.getId().toString());
          tree.setParentId("t" + one.getSysId().toString());
          trees.add(tree);
        }
      }


      Tree<BaseDep> root = TreeUtil.build(trees);
      log.info("----------" + root.toString());
      return JwResponseData.success("获得角色树成功",JacksonUtil.jsonToObj(root.toString(), Map.class));
    } catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('user-group-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/role/get_by_sys_id", method = RequestMethod.GET)
  public JwResponseData<List<BaseRole>> getRoleListByTypeId(@RequestParam(value = "sysId", required = true) int sysIdInput) {
    try {
      List<BaseRole> list = baseRoleMapperEx.selectByExample().where(sysId, isEqualTo(sysIdInput)).and(isValid, isEqualTo(1)).build().execute();
      return JwResponseData.success("根据系统获得角色列表成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('user-group-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/role/add_one", method = RequestMethod.POST)
  public JwResponseData<String> addOneRole(@RequestBody Map jsonObject) {
    try {
      BaseRole br = new BaseRole();
      br.setRoleName(jsonObject.get("roleName").toString());
      br.setRoleDesc(jsonObject.get("roleDesc").toString());
      br.setSysId(Integer.parseInt(jsonObject.get("sysId").toString()));
      br.setUpdateTime(TimeUtil.getCurrentTime());
      br.setIsValid(Integer.parseInt(jsonObject.get("isValid").toString()));
      baseRoleMapperEx.insertAndGetId(br);
      log.info("insert baseRole success");
      int id = br.getId();
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      operationHistoryService.insertHistoryOper(user, operTypeAdd, JacksonUtil.objToJson(jsonObject));
      log.info("send to kafka baserole end,baseRoleId:" + id);
      return JwResponseData.success("新增角色成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('user-group-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/role/update_one", method = RequestMethod.POST)
  public JwResponseData<String> updateOneRole(@RequestBody Map jsonObject) {
    try {
      BaseRole br = new BaseRole();
      br.setId(Integer.parseInt(jsonObject.get("id").toString()));
      br.setRoleName(jsonObject.get("roleName").toString());
      br.setRoleDesc(jsonObject.get("roleDesc").toString());
      br.setSysId(Integer.parseInt(jsonObject.get("sysId").toString()));
      br.setUpdateTime(TimeUtil.getCurrentTime());
      br.setIsValid(Integer.parseInt(jsonObject.get("isValid").toString()));
      baseRoleMapperEx.updateByPrimaryKeySelective(br);
      log.info("update baseRole success");
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update role end operationUser:" + user.getRealName() + ",content:" + JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user, operTypeUpdate, JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("更新角色成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('user-group-manage')")
  @ResponseBody
  @RequestMapping(value = "/api/admin/role/delete_one", method = RequestMethod.POST)
  public JwResponseData<String> deleteOneRole(@RequestBody Map jsonObject) {
    try {
      Integer roleIdInput = Integer.parseInt(jsonObject.get("id").toString());
      long count = baseUserRelRoleMapper.countByExample().where(isValid, isEqualTo(1)).and(roleId, isEqualTo(roleIdInput)).build().execute();
      if (count > 0) {
        return JwResponseData.error(DataAuthResponseCode.ROLE_NOT_DELETE_BECAUSE_HAS_USER, "调用/api/admin/role/delete_one异常");
      }
      BaseRole br = new BaseRole();
      br.setId(roleIdInput);
      br.setUpdateTime(TimeUtil.getCurrentTime());
      br.setIsValid(0);
      baseRoleMapperEx.updateByPrimaryKeySelective(br);
      log.info("delete baseRole end baseRoleId:" + br.getId());
      //增加操作记录
      BaseUser user = UserHolder.getUser();
      if (user == null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("delete role end operationUser:" + user.getRealName() + ",roleId:" + roleIdInput);
      operationHistoryService.insertHistoryOper(user, operTypeDelete, JacksonUtil.objToJson(jsonObject));
      return JwResponseData.success("删除角色成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('user-group-manage')")
  @Operation(summary = "查看指定用户所属用户组接口")
  @RequestMapping(value = "/open_api/role/{userId}", method = RequestMethod.GET)
  public JwResponseData<Map<String, List<BaseRoleRes>>> getByUserId(
          @PathVariable("userId") Integer userId
  ) {
    // 获取所属角色
    List<BaseRole> roleByUserId = baseRoleMapperEx.getRoleByUserId(userId);
    if (CollectionUtils.isEmpty(roleByUserId))
      return JwResponseData.success("", new HashMap<>());

    HashMap<Integer, List<BaseRole>> sysIdAndRoleAuthsMap = EntityUtil.getHashMapOfListUseKeyFieldNameIntegerByObjectList(roleByUserId, "sysId");

    // 获取系统列表
    List<BaseSys> baseSysList = baseSysMapper.selectByExample()
            .where(BaseSysDynamicSqlSupport.id, isIn(sysIdAndRoleAuthsMap.keySet()))
            .and(BaseSysDynamicSqlSupport.isValid, isEqualTo(1))
            .build().execute();
    if (CollectionUtils.isEmpty(baseSysList))
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs("系统列表为空"));

    HashMap<String, BaseSys> idSysMap = EntityUtil.getHashMapUseKeyFieldNameByObjectList(baseSysList, "id");

    Map<String, List<BaseRoleRes>> res = new HashMap<>();
    for (Map.Entry<Integer, List<BaseRole>> integerListEntry : sysIdAndRoleAuthsMap.entrySet()) {
      Integer key = integerListEntry.getKey();
      List<BaseRole> value = integerListEntry.getValue();

      List<BaseRoleRes> resList = new ArrayList<>();
      for (BaseRole baseRole : value) {
        BaseRoleRes baseRoleRes = new BaseRoleRes();
        BeanUtils.copyProperties(baseRole, baseRoleRes);
        resList.add(baseRoleRes);
      }

      BaseSys baseSys = idSysMap.get(key + "");
      if (baseSys == null)
        continue;
      res.put(baseSys.getSysTitle(), resList);
    }
    return JwResponseData.success("获取成功", res);
  }

}

