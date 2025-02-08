package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.*;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.db.dao.BaseSysDynamicSqlSupport;
import cn.cncc.caos.uaa.db.dao.BaseSysMapper;
import cn.cncc.caos.uaa.model.UserHolder;
import cn.cncc.caos.uaa.model.role.auth.BaseRoleAuthReq;
import cn.cncc.caos.uaa.model.role.auth.BaseRoleAuthRes;
import cn.cncc.caos.uaa.model.role.auth.BaseRoleAuthUpdateReq;
import cn.cncc.caos.uaa.service.BaseRoleAuthService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseDep;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRoleAuth;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseSys;
import cn.cncc.caos.uaa.utils.EntityUtil;
import cn.cncc.caos.uaa.utils.Tree;
import cn.cncc.caos.uaa.utils.TreeUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;

@Slf4j
@RestController
@Tag(name = "关于角色安全相关")
public class BaseRoleAuthController {

  @Autowired
  private BaseRoleAuthService baseRoleAuthService;

  private String operTypeAdd = "roleAuthAdd";
  private String operTypeUpdate = "roleAuthUpdate";
  private String operTypeDelete = "roleAuthDelete";

  @Autowired
  private OperationHistoryService operationHistoryService;

  @PreAuthorize("hasAuthority('role-manage')")
  @Operation(summary = "新增角色接口")
  @RequestMapping(value = "/open_api/role/auth/insert", method = RequestMethod.POST)
  public JwResponseData<Object> addRoleAuth(@RequestBody BaseRoleAuthReq baseRoleAuthReq) {
    if (baseRoleAuthReq == null) {
      return JwResponseData.error(JwResponseCode.BIND_ERROR);
    }
    try {
      baseRoleAuthService.addRoleAuth(baseRoleAuthReq);
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      operationHistoryService.insertHistoryOper(user,operTypeAdd, JacksonUtil.objToJson(baseRoleAuthReq));
      return JwResponseData.success("新增成功角色");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('role-manage')")
  @Operation(summary = "修改角色接口")
  @RequestMapping(value = "/open_api/role/auth/update", method = RequestMethod.POST)
  public JwResponseData<Object> updateRoleAuth(@RequestBody BaseRoleAuthUpdateReq baseRoleAuthUpdateReq) {
    try {
      //判断并更新
      if (baseRoleAuthUpdateReq == null)
        return JwResponseData.error(JwResponseCode.BIND_ERROR);
      baseRoleAuthService.updateRoleAuth(baseRoleAuthUpdateReq);
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("update roleAuth end operationUser:"+user.getRealName()+",roleAuthId:"+baseRoleAuthUpdateReq.getId());
      operationHistoryService.insertHistoryOper(user,operTypeUpdate,JacksonUtil.objToJson(baseRoleAuthUpdateReq));
      return JwResponseData.success("修改成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('role-manage')")
  @Operation(summary = "删除角色接口")
  @RequestMapping(value = "/open_api/role/auth/delete", method = RequestMethod.POST)
  public JwResponseData<Object> deleteRoleAuth(@RequestBody Map jsonObject) {
    try {
      JwResponseData<Object> objectJwResponseData = baseRoleAuthService.deleteRoleAuth(jsonObject);
      BaseUser user = UserHolder.getUser();
      if (user==null)
        return JwResponseData.error(JwResponseCode.GET_LOGIN_USER_ERROR);
      log.info("delete roleAuth end operationUser:"+user.getRealName()+",roleAuthId:"+ JacksonUtil.objToJson(jsonObject));
      operationHistoryService.insertHistoryOper(user,operTypeDelete,JacksonUtil.objToJson(jsonObject));
      return objectJwResponseData;
    } catch(IOException e){
      log.error("",e);
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(e.getMessage()));
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('role-manage')")
  @Operation(summary = "根据系统id获取角色接口")
  @RequestMapping(value = "/open_api/role/auth/list/sys/id", method = RequestMethod.GET)
  public JwResponseData<List<BaseRoleAuth>> getRoleAuthListBySysId(@RequestParam(value = "sysId") int sysIdInput) {
    try {
      List<BaseRoleAuth> list = baseRoleAuthService.getRoleAuthListBySysId(sysIdInput);
      return JwResponseData.success("根据系统获得角色列表成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('role-manage')")
  @Operation(summary = "获取所有角色接口")
  @RequestMapping(value = "/open_api/role/auth/list", method = RequestMethod.GET)
  public JwResponseData<List<BaseRoleAuth>> getAllRoleAuth() {
    try {
      List<BaseRoleAuth> list = baseRoleAuthService.getAllRoleAuth();
      return JwResponseData.success("获得所有角色成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/inner_api/role/user/list/key", method = RequestMethod.GET)
  JwResponseData<List<Map<String, Object>>> getOperationDepRoleRelUser() {
    try {
      List<Map<String, Object>> list = baseRoleAuthService.getRoleAndUsersByRoleKey();
      log.info(JacksonUtil.objToJson(list));//JSONArray.toJSONString(list)
      return JwResponseData.success("根据系统获得角色和人员对应列表成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAuthority('role-manage')")
  @RequestMapping(value = {"/inner_api/role/auth/operation/dep/role", "/open_api/role/auth/operation/dep/role"}, method = RequestMethod.GET)
//    @RequestMapping(value = {"/api/role/get_role_by_sys_name","/open_api/role/get_role_by_sys_name"}, method = RequestMethod.GET)
  JwResponseData<List<BaseRoleAuth>> getOperationDepRole() {
    try {
      List<BaseRoleAuth> list = baseRoleAuthService.getRoleByRoleKey();
      return JwResponseData.success("根据系统获得角色成功", list);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @Autowired
  private BaseSysMapper baseSysMapper;

  @ResponseBody
  @RequestMapping(value = "/open_api/role/auth/tree", method = RequestMethod.GET)
  @PreAuthorize("hasAuthority('role-manage')")
  public JwResponseData<Map> getAllRoleAuthTree(@RequestParam(value = "isShowRole", required = false, defaultValue = "0") int isShowRole) {

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
        List<BaseRoleAuth> listRole = baseRoleAuthService.getAllRoleAuth();
        for (BaseRoleAuth one : listRole) {
          Tree<BaseDep> tree = new Tree<BaseDep>();
          tree.setText(one.getRoleName());
          tree.setId(one.getId());
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
    }catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  @Operation(summary = "查看指定用户所属角色接口")
  @RequestMapping(value = "/open_api/role/auth/{userId}", method = RequestMethod.GET)
  @PreAuthorize("hasAuthority('role-manage')")
  public JwResponseData<Map<String, List<BaseRoleAuthRes>>> getByUserId(
          @PathVariable("userId") Integer userId
  ) {
    // 获取所属角色
    List<BaseRoleAuth> roleByUserId = baseRoleAuthService.getRoleByUserId(userId);
    if (CollectionUtils.isEmpty(roleByUserId))
      return JwResponseData.success("", new HashMap<>());

    HashMap<Integer, List<BaseRoleAuth>> sysIdAndRoleAuthsMap = EntityUtil.getHashMapOfListUseKeyFieldNameIntegerByObjectList(roleByUserId, "sysId");

    // 获取系统列表
    List<BaseSys> baseSysList = baseSysMapper.selectByExample()
            .where(BaseSysDynamicSqlSupport.id, isIn(sysIdAndRoleAuthsMap.keySet()))
            .and(BaseSysDynamicSqlSupport.isValid, isEqualTo(1))
            .build().execute();
    if (CollectionUtils.isEmpty(baseSysList))
      return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs("系统列表为空"));

    HashMap<String, BaseSys> idSysMap = EntityUtil.getHashMapUseKeyFieldNameByObjectList(baseSysList, "id");

    Map<String, List<BaseRoleAuthRes>> res = new HashMap<>();
    for (Map.Entry<Integer, List<BaseRoleAuth>> integerListEntry : sysIdAndRoleAuthsMap.entrySet()) {
      Integer key = integerListEntry.getKey();
      List<BaseRoleAuth> value = integerListEntry.getValue();

      List<BaseRoleAuthRes> resList = new ArrayList<>();
      for (BaseRoleAuth baseRoleAuth : value) {
        BaseRoleAuthRes baseRoleAuthRes = new BaseRoleAuthRes();
        BeanUtils.copyProperties(baseRoleAuth, baseRoleAuthRes);
        resList.add(baseRoleAuthRes);
      }

      BaseSys baseSys = idSysMap.get(key + "");
      if (baseSys == null)
        continue;
      res.put(baseSys.getSysTitle(), resList);
    }
    return JwResponseData.success("获取成功", res);
  }
}
